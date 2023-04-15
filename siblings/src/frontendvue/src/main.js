import { createApp } from 'vue'
import App from './App.vue'
import router from './router.js'
import axios from 'axios'

let app = createApp(App);
app.config.globalProperties.axios = axios;

import store from './store.js'

app.use(store).use(router).mount('#app')