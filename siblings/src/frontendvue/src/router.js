import { createRouter, createWebHistory } from "vue-router";
import Login from "./components/Login.vue";
import Index from "./components/Index.vue";

const routes = [
    {
        path:"/",
        component: Index,
    }
];

const router = createRouter({
    history : createWebHistory(),
    routes
});
export default router;