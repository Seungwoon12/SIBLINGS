import { createRouter, createWebHistory } from "vue-router";
import MyPage from "./components/MyPage.vue";
import Redirect from "./components/Redirect.vue";
import Index from "./components/Index.vue";

const routes = [
    {
        path:"/",
        component: Index,
    },
    {
        path:"/oauth/redirect",
        component : MyPage,
    },
    // {
    //     path:"/my",
    //     component : MyPage,
    // }
];

const router = createRouter({
    history : createWebHistory(),
    routes
});
export default router;