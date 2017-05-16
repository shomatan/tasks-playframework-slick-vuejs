<template>
    <div>
        <div class="form-group">
            <label for="title-txt">Title</label>
            <input id="title-txt" name="title" type="text" class="form-control" v-model="task.title" placeholder="title"/>
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
        created: function () {

        },
        data: function () {
            return {
                task: { title: '' }
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
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
            },
            deletePhrase (id) {
                console.log("in delete:" + id);
                var m = jsRoutes.controllers.HomeController.deletePhrase(id);
                console.log("url:" + m.url);

                this.$http.delete(m.url).then(function(response) {
                    console.log("Phrase deleted!");
                    this.$router.go('/home');
                }).catch(function(error) {
                    console.log("has error" + error);
                });
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