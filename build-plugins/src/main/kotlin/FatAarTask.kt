// Copyright 2026, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/**
 * Merges several Android library AARs into one fat AAR.
 *
 * All `classes.jar` entries are combined into a single jar; the manifest and
 * other entries are taken from the first input that provides them (the main
 * module should be listed first). Duplicate entries are skipped.
 */
abstract class FatAarTask : DefaultTask() {
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val inputAars: ConfigurableFileCollection

    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    @TaskAction
    fun merge() {
        val mergedClasses = linkedMapOf<String, ByteArray>()
        val otherEntries = linkedMapOf<String, ByteArray>()
        val manifests = linkedMapOf<String, ByteArray>()

        for (aar in inputAars.files) {
            ZipFile(aar).use { aarZip ->
                for (entry in aarZip.entries()) {
                    if (entry.isDirectory) continue
                    val bytes = aarZip.getInputStream(entry).use { it.readBytes() }
                    when (entry.name) {
                        "classes.jar" ->
                            readNestedJar(bytes) { name, data ->
                                if (!mergedClasses.containsKey(name)) {
                                    mergedClasses[name] = data
                                }
                            }
                        "AndroidManifest.xml" -> manifests.putIfAbsent(aar.name, bytes)
                        else -> otherEntries.putIfAbsent(entry.name, bytes)
                    }
                }
            }
        }

        // Prefer the main module's manifest when multiple AARs provide one.
        val manifest =
            manifests.entries.firstOrNull { it.key.startsWith("elyon-ui") }?.value
                ?: manifests.values.firstOrNull()

        val out = outputFile.get().asFile
        out.parentFile.mkdirs()
        ZipOutputStream(out.outputStream().buffered()).use { zos ->
            manifest?.let { writeEntry(zos, "AndroidManifest.xml", it) }
            writeEntry(zos, "classes.jar", buildJar(mergedClasses))
            otherEntries.forEach { (name, bytes) -> writeEntry(zos, name, bytes) }
        }
    }

    private fun readNestedJar(bytes: ByteArray, sink: (name: String, data: ByteArray) -> Unit) {
        ZipInputStream(bytes.inputStream().buffered()).use { zis ->
            var entry = zis.nextEntry
            while (entry != null) {
                if (!entry.isDirectory && entry.name != "META-INF/MANIFEST.MF") {
                    sink(entry.name, zis.readBytes())
                }
                zis.closeEntry()
                entry = zis.nextEntry
            }
        }
    }

    private fun buildJar(entries: Map<String, ByteArray>): ByteArray {
        val bos = ByteArrayOutputStream()
        ZipOutputStream(bos).use { zos ->
            entries.forEach { (name, bytes) -> writeEntry(zos, name, bytes) }
        }
        return bos.toByteArray()
    }

    private fun writeEntry(zos: ZipOutputStream, name: String, bytes: ByteArray) {
        zos.putNextEntry(ZipEntry(name))
        zos.write(bytes)
        zos.closeEntry()
    }
}
