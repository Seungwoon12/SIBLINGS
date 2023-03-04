import { createApp } from 'vue'
import App from './App.vue'
import router from './router.js'
import vue3GoogleLogin from "vue3-google-login"
import axios from 'axios'

let app = createApp(App);
app.config.globalProperties.axios = axios;

app.use(router).use(vue3GoogleLogin,{
    clientId : process.env.VUE_APP_GOOGLE_CLIENT_ID
}).mount('#app')
