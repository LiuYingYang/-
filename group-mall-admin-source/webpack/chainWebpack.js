/*
 * @description: 
 * @Author: vikingShip
 * @Date: 2021-08-21 09:28:28
 * @LastEditors: vikingShip
 * @LastEditTime: 2021-09-03 17:55:03
 */
const cdnDependencies = require("./dependencies-cdn");
const { set, each } = require("lodash");

const cdn = {
  css: cdnDependencies.map(e => e.css).filter(e => e),
  js: cdnDependencies.map(e => e.js).filter(e => e),
};

module.exports = config => {
  config.plugins.delete("fork-ts-checker");

  const targetHtmlPluginNames = ["html"];

  each(targetHtmlPluginNames, name => {
    config.plugin(name).tap(options => {
      set(options, "[0].cdn", process.env.NODE_ENV === "production" ? cdn : []);
      return options;
    });
  });

  // 容量分析
  if (process.env.ANALYZER === "1") {
    config
      .plugin("webpack-bundle-analyzer")
      .use(require("webpack-bundle-analyzer").BundleAnalyzerPlugin);
  }
};
