<template>
  <div>
    <header class="navbar navbar-inverse navbar-static-top">
      <div class="container">
        <router-link to="/" class="navbar-brand">TODO App</router-link>
        <ul class="nav navbar-nav pull-right">
          <li><router-link to="/add">Add</router-link></li>
        </ul>
        <form class="navbar-form navbar-right">
          <input type="text" class="form-control" placeholder="New task...">
        </form>
      </div>
    </header>
    <div class="container">
      <router-view></router-view>
    </div>
  </div>
</template>
