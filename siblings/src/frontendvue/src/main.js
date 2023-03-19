import { createApp } from 'vue'
import App from './App.vue'
import router from './router.js'
import axios from 'axios'

let app = createApp(App);
app.config.globalProperties.axios = axios;

app.use(router).mount('#app')