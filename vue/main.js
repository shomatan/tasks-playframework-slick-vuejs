// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import 'bootstrap/dist/css/bootstrap.css';
import Vue from 'vue'
import App from './App.vue'
import Home from './components/Home.vue'

import VueRouter from 'vue-router'

Vue.config.productionTip = false

Vue.use(VueRouter)

// Pointing routes to the components they should use
var router = new VueRouter({
    routes: [
        {
            path: '/',
            component: Home
        }
    ]
})

new Vue({
    el: '#app',
    router,
    template: '<App/>',
    components: { App }
})
