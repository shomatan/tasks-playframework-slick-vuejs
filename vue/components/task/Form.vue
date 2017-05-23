<template>
    <div>
        <div class="form-group">
            <label for="title-txt">Title</label>
            <input id="title-txt" name="title" type="text" class="form-control" v-model="task.title" placeholder="title"/>
            <label for="content-txt">Content</label>
            <textarea id="content-txt" name="content" type="text" class="form-control" v-model="task.content" placeholder="content"/>
            <label for="deadlineAt-date">Deadline at</label>
            <el-date-picker id="deadlineAt-date" name="deadlineAt" v-model="task.deadlineAt" type="datetime" placeholder="Select date and time"></el-date-picker>
        </div>
        <div class="form-group">
            <input type="submit" class="btn btn-primary" @click="save()" />
            <input type="submit" value="Delete task" class="btn btn-danger" v-if="task.hasOwnProperty('id')" @click="deleteTask(task.id)" />
        </div>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        data: function () {
            return {
                task: {
                    title: '',
                    content: "",
                    deadlineAt: ""
                }
            }
        },

        created: function () {
            var self = this
            if (typeof self.$route.params.taskId !== 'undefined') {
                var m = jsRoutes.controllers.TaskController.edit(this.$route.params.taskId)
                console.log("hello lady:" + m.url)

                axios.get(m.url)
                    .then(function (response) {
                        self.task = response.data
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
            }
        },

        methods: {
            save () {
                var self = this
                console.log("in save:" + JSON.stringify(self.task))

                function m(task) {
                    if (task["id"]) {
                        return jsRoutes.controllers.TaskController.editTask(task.id)
                    } else {
                        return jsRoutes.controllers.TaskController.addTask()
                    }
                }

                console.log("url:" + m(self.task).url)

                axios.post(m(self.task).url, self.task)
                    .then(function (response) {
                        console.log("Task saved!")
                        self.$router.push({ name: 'home' } )
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
            },
            deleteTask (id) {
                var self = this
                console.log("in delete:" + id);
                var m = jsRoutes.controllers.TaskController.deleteTask(id);
                console.log("url:" + m.url);

                axios.delete(m.url)
                    .then(function (response) {
                        console.log("Task deleted!")
                        self.$router.push({ name: 'home' } )
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    h1 {
        color: #42b983;
    }
</style>