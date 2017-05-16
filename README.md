# tasks-playframework-slick-vuejs

This application is used to showcase the Playframework as well as Vue.js
while adding tasks. This application makes use of the following:

- [Playframework 2.5](http://www.playframework.com)
  - [Javascript Routing](https://www.playframework.com/documentation/2.5.x/ScalaJavascriptRouting)
  - [JSON Automated Mapping](https://www.playframework.com/documentation/2.5.x/ScalaJsonAutomated)
  - [Scala Forms](https://www.playframework.com/documentation/2.5.x/ScalaForms)
- [Vue.js](https://vuejs.org/)
  - [Vue Router](http://router.vuejs.org/en/index.html)
  - [axios](https://github.com/mzabriskie/axios)
- [Node.js](https://nodejs.org/en)
- [Webpack](https://webpack.github.io)
- [Bootstrap](http://www.bootstrap.com) 
- [Slick](http://www.slick.typesafe.com)

## Usage - development

    npm install
    docker-compose up -d
    activator ~run
    webpack --watch