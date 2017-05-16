<template>
    <div>
        <h1>Edit task</h1>
        <task-form></task-form>
    </div>
</template>

<script>
    import TaskForm from './Form.vue'

    export default {
        components: {
            TaskForm
        },
    }
</script>