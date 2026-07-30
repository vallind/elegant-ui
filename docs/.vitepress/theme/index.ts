import DefaultTheme from 'vitepress/theme'
import ButtonPreview from './components/ButtonPreview.vue'
import './style.css'

export default {
  extends: DefaultTheme,
  enhanceApp({ app }) {
    app.component('ButtonPreview', ButtonPreview)
  },
}
