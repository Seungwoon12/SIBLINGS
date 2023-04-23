import { createRouter, createWebHistory } from "vue-router";
import MyPage from "./components/MyPage.vue";
import Index from "./components/Index.vue";
import Redirect from "./components/Redirect.vue";
import Login from "./components/Login.vue";
import Home from "./components/Home.vue";

const routes = [
    {
        path:"/",
        component: Index,
        children : [
            {
                path:"/",
                component: Home,
            },
            {
                path:"login",
                component: Login,
            },
            {
                path:"my",
                component : MyPage,
            },
        ]
    },
    {
        path:"/oauth/redirect",
        component : Redirect,
    },
];

const router = createRouter({
    history : createWebHistory(),
    routes
});
export default router;