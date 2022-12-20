// import PersonList from ;
// import PersonEdit from '@/components/PersonEdit.vue';

import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";

const routes: Array<RouteRecordRaw> = [
  { name: 'Home', path: '/', component: import('../components/PersonList.vue') },
  { name: 'AddPerson', path: '/person/add', component: import('../components/PersonEdit.vue') },
]

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;