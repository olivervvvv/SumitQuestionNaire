import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import logi from '../components/logiCN.vue'
import logiEN from '../components/logiEN.vue'
import sign from '../components/sign.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path:'/logi',
      name:'logi',
      component: logi
    },
    {
      path:'/logiEN',
      name:'logiEN',
      component: logiEN
    },
    // {
    //   path:'/logiJP',
    //   name:'logiJP',
    //   component: logi
    // },
    {
      path:'/sign',
      name:'sign',
      component:sign
    },
    {
      path:'/math',
      name:'math',
      component:()=>import('../views/mathtest.vue')
    },
    {
      path:'/test',
      name:'test',
      component:()=>import('../views/test.vue'),
      children:[
        {
          path:'/searchtest',
          name:'searchtest',
          component:()=>import('../components/searchquiz.vue')
        },
        {
          path:'/bulidtest',
          name:'buildtest',
          component:()=>import('../components/bulidtest.vue')
        },
      ]
    }
  ]
})

export default router
