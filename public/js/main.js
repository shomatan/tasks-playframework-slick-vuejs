
var TaskList = {
    template: '#task-list',
    data: function () {
        return {
            isLoading: false,
            tasks: [],
            error: null
        }
    },

    created: function () {
        this.fetchData()
    },

    watch: {
        '$route': 'fetchData'
    },

    methods: {
        fetchData: function () {
            var self = this
            self.isLoading = true
            axios.get('/api/v1/tasks')
                .then(function (response) {
                    self.tasks = response.data
                    self.isLoading = false
                })
                .catch(function (error) {
                    self.fetchError = error
                    self.isLoading = false
                })
        }
    }
}

var router = new VueRouter({
    routes: [
        {
            path: '/',
            component: TaskList
        }
    ]
})

new Vue({
    router: router
}).$mount('#app')
