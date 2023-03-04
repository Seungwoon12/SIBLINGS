import { createRouter, createWebHistory } from "vue-router";
import Login from "./components/Login.vue";
import Home from "./components/Home.vue";
import Authenticate from "./components/Authenticate.vue";

const routes = [
    {
        path : "/login",
        component : Login
    },
    {
        path : "/authenticate",
        component : Authenticate
    },
    {
        path:"/",
        component: Home
    }
];

const router = createRouter({
    history : createWebHistory(),
    routes
});
export default router;