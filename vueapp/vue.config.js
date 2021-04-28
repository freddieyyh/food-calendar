module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  outputDir: "../src/main/resources/static",
  indexPath: "index.html",
  devServer: {
    proxy: {
      '^/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
    disableHostCheck: true,
  }
}
