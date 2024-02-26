import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import loginForm from "@/views/LoginForm.vue";
import registerForm from "@/views/RegisterForm.vue";
import clientHome from "@/views/ClientHome.vue";
import clientPersonal from "@/views/ClientPersonal.vue";
import adminTeachers from "@/views/AdminTeachers.vue";
import adminCourses from "@/views/AdminCourses.vue";
import adminTeachings from "@/views/AdminTeachings.vue";
import adminLessons from "@/views/AdminLessons.vue";

const routes = [
    {
        path: '/',
        name: 'home',
        component: HomeView
    },
    {
        path: '/LoginForm',
        name: 'Login',
        component: loginForm
    },
    {
        path: '/RegisterForm',
        name: 'Register',
        component: registerForm
    },
    {
        path: '/ClientHome',
        name: 'ClientHome',
        component: clientHome
    },
    {
        path: '/ClientPersonal',
        name: 'ClientPersonal',
        component: clientPersonal
    },
    {
        path: '/AdminTeachers',
        name: 'AdminTeachers',
        component: adminTeachers
    },
    {
        path: '/AdminCourses',
        name: 'AdminCourses',
        component: adminCourses
    },
    {
        path: '/AdminTeachings',
        name: 'AdminTeachings',
        component: adminTeachings
    },
    {
        path: '/AdminLessons',
        name: 'AdminLessons',
        component: adminLessons
    }

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
