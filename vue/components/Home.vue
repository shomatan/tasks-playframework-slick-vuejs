<template>
    <div>
        <div class="heading">
            <h1>My TODO!</h1>
            <img class="logo" src="../images/top.png" />
        </div>

        <div class="alert alert-info">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <p>
                This application is used to showcase the Playframework as well as Vue.js
                while adding tasks. This application makes use of the following:
            </p>
            <ul>
                <li><a href="http://www.playframework.com">Playframework 2.5</a></li>
                <ul>
                    <li><a href="https://www.playframework.com/documentation/2.5.x/ScalaJavascriptRouting">Javascript Routing</a></li>
                    <li><a href="https://www.playframework.com/documentation/2.5.x/ScalaJsonAutomated">JSON Automated Mapping</a></li>
                    <li><a href="https://www.playframework.com/documentation/2.5.x/ScalaForms">Scala Forms</a></li>
                </ul>
                <li><a href="https://vuejs.org/">Vue.js</a></li>
                <ul>
                    <li><a href="http://router.vuejs.org/en/index.html">Vue Router</a></li>
                    <li><a href="https://github.com/vuejs/vue-resource">Vue Resource</a></li>
                </ul>
                <li><a href="https://nodejs.org/en">Node.js</a></li>
                <li><a href="https://webpack.github.io">Webpack</a></li>
                <li><a href="http://www.bootstrap.com">Bootstrap</a></li>
                <li><a href="http://www.slick.typesafe.com">Slick</a></li>
            </ul>
        </div>
        <h2>{{ tasks.length }} tasks found</h2>
        <div>
            <div class="panel panel-default">
                <div class="panel-heading">Tasks</div>
                <table class="task-tbl table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>Title</th>
                        <th>Created at</th>
                    </tr>
                    </thead>
                    <tbody v-for="p in tasks">
                    <tr>
                        <td>{{ p.title }}</td>
                        <td>{{ p.createdAt }}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
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
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .heading {
        overflow: auto;
        padding-bottom: 10px;
    }

    .heading h1 {
        float: left;
    }

    .logo {
        width: 200px;
        height: 136px;
        float: right;
    }

    .task-tbl tr {
        cursor: pointer;
    }

</style>
