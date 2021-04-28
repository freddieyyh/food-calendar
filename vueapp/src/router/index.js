import Vue from 'vue'
import VueRouter from 'vue-router'
import { routes, routeName } from './router'
import axios from "axios";

Vue.use(VueRouter)

const router = new VueRouter({
  routes,
  mode: "history"
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    axios.get('/api/auth/check', {}).then((response) => {
      if (response.data) {
        next();
      } else {
        next({
          name: routeName.login,
        });
      }
    }).catch((err) => {
      console.log(err)
    });
  } else {
    next();
  }
})

export default router
