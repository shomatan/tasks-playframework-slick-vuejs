var path = require('path')

module.exports = {
  entry: './vue/main.js',
  output: {
    path: path.resolve(__dirname, 'public/javascripts'),
    publicPath: 'public/javascripts/',
    filename: 'bundle.js'
  },
  module: {
    loaders: [
      { test: /\.vue$/, loader: 'vue-loader' }
    ]
  },
  resolve: {
    extensions: ['.js', '.vue'],
    modules: [
        "node_modules"
    ],
    alias: {
        vue: 'vue/dist/vue.common.js'
    }
  }
}
