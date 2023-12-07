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
          path:'/buildQuestion',
          name:'buildQuestion',
          component:()=>import('../components/buildQuestion.vue')
        },
        {
          path:'/searchQuestion',
          name:'searchQuestion',
          component:()=>import('../components/searchQuestion.vue')
        },
        {
          path:'/newUpdateQuestion',
          name:'newUpdateQuestion',
          component:()=>import('../components/newUpdateQuestion.vue')
        },
        {
          path:'/updateQuestion',
          name:'updateQuestion',
          component:()=>import('../components/updateQuestion.vue')
        },
        {
          path:'/backhome',
          name:'backhome',
          component:()=>import('../components/backquestionhome.vue')
        },
      ]
    },
    {
      path:'/questionnaire/:id',
      name:'questionnaire-detail',
      component:()=>import('../components/QuestionnaireDetail.vue')
    },
    {      
    path:'/updatepage/:id',
    name:'updatepage',
    component:()=>import('../components/updatepage.vue')
    },
  ]
})

export default router
