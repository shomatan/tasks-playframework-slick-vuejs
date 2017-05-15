// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App.vue'

import VueRouter from 'vue-router'

Vue.config.productionTip = false

Vue.use(VueRouter)

const router = new VueRouter()

// Pointing routes to the components they should use
router.map({

})

// Any invalid route will redirect to home
router.redirect({
  '*': '/'
})

router.start(App, '#app')
