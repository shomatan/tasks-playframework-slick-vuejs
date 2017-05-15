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
      {
        test: /\.vue$/,
        loader: 'vue-loader'
      },
      {
        test: /\.png$/,
        loader: "url-loader?mimetype=image/png"
      },
      { test: /\.css$/, loader: 'style-loader!css-loader' }
    ]
  },
  resolve: {
    extensions: ['.js', '.vue', '.css'],
    modules: [
        "node_modules"
    ],
    alias: {
        vue: 'vue/dist/vue.common.js'
    }
  }
}
