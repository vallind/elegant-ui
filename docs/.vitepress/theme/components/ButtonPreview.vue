<script setup lang="ts">
import { computed, ref } from 'vue'
import { useData } from 'vitepress'

type Style = 'primary' | 'secondary' | 'tertiary'
type Size = 'small' | 'medium' | 'large'

const { lang } = useData()
const isChinese = computed(() => lang.value.startsWith('zh'))
const dark = ref(false)
const loading = ref(false)
const disabled = ref(false)
const selectedStyle = ref<Style>('primary')
const selectedSize = ref<Size>('medium')
const clickCount = ref(0)

const copy = computed(() =>
  isChinese.value
    ? {
        preview: '交互预览',
        style: '样式',
        size: '尺寸',
        theme: '深色',
        loading: '加载中',
        disabled: '禁用',
        action: '继续',
        count: '有效点击',
        note: '此处用于快速视觉确认；最终交互仍以 Android APK 真机验证为准。',
      }
    : {
        preview: 'Interactive preview',
        style: 'Style',
        size: 'Size',
        theme: 'Dark',
        loading: 'Loading',
        disabled: 'Disabled',
        action: 'Continue',
        count: 'Accepted clicks',
        note: 'Use this for fast visual review. The Android APK remains the interaction source of truth.',
      },
)

const styles: Style[] = ['primary', 'secondary', 'tertiary']
const sizes: Size[] = ['small', 'medium', 'large']

function activate() {
  if (!disabled.value && !loading.value) clickCount.value += 1
}
</script>

<template>
  <div class="elegant-preview" :class="{ 'is-dark': dark }">
    <div class="preview-toolbar">
      <strong>{{ copy.preview }}</strong>
      <label class="switch-control">
        <input v-model="dark" type="checkbox" />
        <span>{{ copy.theme }}</span>
      </label>
    </div>

    <div class="preview-canvas">
      <button
        type="button"
        class="preview-touch-target"
        :disabled="disabled || loading"
        @click="activate"
      >
        <span
          class="preview-button"
          :class="[`style-${selectedStyle}`, `size-${selectedSize}`]"
        >
          <span v-if="loading" class="spinner" aria-hidden="true" />
          <span>{{ loading ? copy.loading : copy.action }}</span>
          <span aria-hidden="true">→</span>
        </span>
      </button>
      <span class="click-count">{{ copy.count }}: {{ clickCount }}</span>
    </div>

    <div class="preview-controls">
      <fieldset>
        <legend>{{ copy.style }}</legend>
        <button
          v-for="style in styles"
          :key="style"
          type="button"
          :class="{ selected: selectedStyle === style }"
          @click="selectedStyle = style"
        >
          {{ style }}
        </button>
      </fieldset>

      <fieldset>
        <legend>{{ copy.size }}</legend>
        <button
          v-for="size in sizes"
          :key="size"
          type="button"
          :class="{ selected: selectedSize === size }"
          @click="selectedSize = size"
        >
          {{ size }}
        </button>
      </fieldset>

      <div class="toggle-row">
        <label><input v-model="loading" type="checkbox" /> {{ copy.loading }}</label>
        <label><input v-model="disabled" type="checkbox" /> {{ copy.disabled }}</label>
      </div>
    </div>

    <p class="preview-note">{{ copy.note }}</p>
  </div>
</template>

<style scoped>
.elegant-preview {
  --preview-bg: #f4f4f8;
  --preview-surface: #ffffff;
  --preview-text: #1f2028;
  --preview-muted: #6f707b;
  --preview-border: rgba(31, 32, 40, 0.14);
  --preview-primary: #6253e8;
  --preview-primary-pressed: #5142d0;
  margin: 24px 0 32px;
  overflow: hidden;
  border: 1px solid var(--preview-border);
  border-radius: 20px;
  background: var(--preview-surface);
  color: var(--preview-text);
  box-shadow: 0 18px 50px rgba(32, 28, 63, 0.08);
}

.elegant-preview.is-dark {
  --preview-bg: #111116;
  --preview-surface: #1a1a21;
  --preview-text: #f4f3f8;
  --preview-muted: #a7a5b1;
  --preview-border: rgba(255, 255, 255, 0.12);
  --preview-primary: #9c90ff;
  --preview-primary-pressed: #887aff;
}

.preview-toolbar,
.preview-controls {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  padding: 16px 18px;
}

.preview-toolbar {
  border-bottom: 1px solid var(--preview-border);
}

.preview-canvas {
  display: flex;
  min-height: 210px;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 14px;
  padding: 32px;
  background:
    radial-gradient(circle at 50% 0%, rgba(98, 83, 232, 0.13), transparent 55%),
    var(--preview-bg);
}

.preview-touch-target {
  display: inline-flex;
  min-width: 120px;
  min-height: 48px;
  align-items: center;
  justify-content: center;
  border: 0;
  padding: 0;
  background: transparent;
  color: inherit;
  font: inherit;
  cursor: pointer;
}

.preview-button {
  display: inline-flex;
  min-width: 120px;
  align-items: center;
  justify-content: center;
  gap: 9px;
  border: 1px solid transparent;
  font: inherit;
  font-weight: 650;
  letter-spacing: -0.01em;
  transition: transform 120ms ease, background 120ms ease, border-color 120ms ease,
    opacity 120ms ease;
}

.preview-touch-target:active:not(:disabled) .preview-button {
  transform: scale(0.975);
}

.preview-touch-target:focus-visible {
  outline: none;
}

.preview-touch-target:focus-visible .preview-button {
  outline: 3px solid color-mix(in srgb, var(--preview-primary) 42%, transparent);
  outline-offset: 3px;
}

.preview-touch-target:disabled {
  cursor: not-allowed;
}

.preview-touch-target:disabled .preview-button {
  opacity: 0.46;
}

.style-primary {
  background: var(--preview-primary);
  color: #ffffff;
}

.preview-touch-target:hover:not(:disabled) .style-primary {
  background: var(--preview-primary-pressed);
}

.style-secondary {
  border-color: var(--preview-border);
  background: var(--preview-surface);
  color: var(--preview-text);
}

.preview-touch-target:hover:not(:disabled) .style-secondary,
.preview-touch-target:hover:not(:disabled) .style-tertiary {
  background: color-mix(in srgb, var(--preview-primary) 9%, var(--preview-surface));
}

.style-tertiary {
  background: transparent;
  color: var(--preview-primary);
}

.size-small {
  min-height: 36px;
  border-radius: 10px;
  padding: 0 12px;
  font-size: 13px;
}

.size-medium {
  min-height: 40px;
  border-radius: 12px;
  padding: 0 16px;
  font-size: 14px;
}

.size-large {
  min-height: 48px;
  border-radius: 14px;
  padding: 0 20px;
  font-size: 15px;
}

.spinner {
  width: 15px;
  height: 15px;
  border: 2px solid currentColor;
  border-right-color: transparent;
  border-radius: 999px;
  animation: spin 700ms linear infinite;
}

.click-count,
.preview-note {
  color: var(--preview-muted);
  font-size: 12px;
}

.preview-controls {
  flex-wrap: wrap;
  align-items: flex-end;
  border-top: 1px solid var(--preview-border);
}

fieldset {
  display: flex;
  gap: 6px;
  margin: 0;
  padding: 0;
  border: 0;
}

legend {
  margin-bottom: 6px;
  color: var(--preview-muted);
  font-size: 11px;
  font-weight: 650;
  text-transform: uppercase;
  letter-spacing: 0.06em;
}

fieldset button {
  border: 1px solid var(--preview-border);
  border-radius: 8px;
  padding: 6px 9px;
  background: transparent;
  color: var(--preview-text);
  font: inherit;
  font-size: 12px;
  cursor: pointer;
  text-transform: capitalize;
}

fieldset button.selected {
  border-color: var(--preview-primary);
  background: color-mix(in srgb, var(--preview-primary) 12%, transparent);
  color: var(--preview-primary);
}

.toggle-row {
  display: flex;
  gap: 14px;
  color: var(--preview-muted);
  font-size: 12px;
}

.switch-control {
  display: flex;
  align-items: center;
  gap: 7px;
  color: var(--preview-muted);
  font-size: 12px;
}

.preview-note {
  margin: 0;
  padding: 0 18px 16px;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

@media (max-width: 640px) {
  .preview-controls {
    align-items: stretch;
    flex-direction: column;
  }

  .preview-canvas {
    min-height: 180px;
    padding: 24px 16px;
  }
}
</style>
