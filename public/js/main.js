axios.get('/api/v1/tasks')
    .then(function (response) {
        initVue(response.data);
    })
    .catch(function (error) {
        console.log(error);
    });

function initVue(tasks){
    new Vue({
        el: '#app',
        data: {
            tasks: tasks
        },
        mounted: function(){
            document.getElementById("app").style.display = "";
        }
    })
}
