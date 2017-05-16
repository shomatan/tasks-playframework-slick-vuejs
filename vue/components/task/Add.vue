<template>
    <div>
        <h1>Add task</h1>
        <task-form></task-form>
    </div>
</template>

<script>
    import TaskForm from './Form.vue'

    export default {
        components: {
            TaskForm
        }
    }
</script>