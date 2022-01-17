/*
 * @description: 抽离开源版本
 * @Author: chuyinlong
 * @Date: 2021-08-20 17:29:09
 * @LastEditors: vikingShip
 * @LastEditTime: 2021-08-31 17:43:06
 */
import Vue from "vue";
import Router from "vue-router";
import { beforeCallBackasync, afterCallBackasync } from "@/routes/tool";

Vue.use(Router);

//获取原型对象上的push函数
const originalPush = Router.prototype.push;
//修改原型对象中的push方法
Router.prototype.push = function push(location) {
  return originalPush.call(this, location) as any;
};

const constantRouterMap = [
  {
    path: "/sign",
    name: "sign",
    component: () =>
      import(/* webpackChunkName: "sign" */ "@/views/sign/Index.vue"),
    meta: {
      title: "登录",
      isShow: 1,
    },
  },
];

const router = new Router({
  mode: "history",
  base: process.env.VUE_APP_BASEPATH,
  routes: constantRouterMap,
});

router.beforeEach(beforeCallBackasync);

router.afterEach(afterCallBackasync);

export default router;
