<template>
    <div>
        <div class="heading">
            <h1>My tasks</h1>
            <img class="logo" src="../images/top.png" />
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
                    <tr @click="edit(p.id)">
                        <td>{{ p.title }}</td>
                        <td>{{ p.createdAt | formatDate }}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import moment from 'moment';

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

        filters: {
            formatDate: function (date) {
                return moment(date).format('YYYY/MM/DD HH:mm:ss');
            }
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
            },
            edit: function (id) {
                console.log("id:" + id);
                this.$router.push({ name: 'edit', params : { taskId: id }} );
            },
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
        width: 136px;
        height: 136px;
        float: right;
    }

    .task-tbl tr {
        cursor: pointer;
    }

</style>
