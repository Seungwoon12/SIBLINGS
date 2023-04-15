import { createRouter, createWebHistory } from "vue-router";
import MyPage from "./components/MyPage.vue";
import Index from "./components/Index.vue";
import Redirect from "./components/Redirect.vue";

const routes = [
    {
        path:"/",
        component: Index,
    },
    {
        path:"/oauth/redirect",
        component : Redirect,
    },
    {
        path:"/my",
        component : MyPage,
    }
];

const router = createRouter({
    history : createWebHistory(),
    routes
});
export default router;