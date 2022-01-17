/*
 * @description:
 * @Author: vikingShip
 * @Date: 2021-07-17 13:29:52
 * @LastEditors: vikingShip
 * @LastEditTime: 2021-08-31 17:36:00
 */
import Vue from "vue";
import { menuStore } from "@/store/modules/menu";
import { userStore } from "@/store/modules/user";
import router from "@/routes/index";
import config from "@/config";
import storage from "@/libs/storage";
import { getSystemConfig } from "@/api/sign";
import { logout, modifySignStatus } from "@/libs";
import { getShopInfo } from "@/api/businessCenter/setting";
import {Route } from "vue-router";

// no redirect whitelist
const whiteList = ["/sign", "/redirect/sign", "/0.1/sign", "/console"];

const timer = {
  time: null as number | null,
  set() {
    return new Promise(() => {
      this.time = setTimeout(() => {
        Vue.prototype.$Progress.fail();
        // reject();
      }, 20000);
    });
  },
  clear() {
    clearTimeout(Number(this.time));
  },
};

async function hasToken(to: Route, from: Route, next: any, token: string) {
  modifySignStatus(token, storage.get("userInfo"));
  /* has token and validate*/
  if (!menuStore.routers.length) {
    menuStore.generateRoutes().then(() => {
      router.addRoutes(menuStore.routers);
      next({ ...to, replace: true });
    });
  } else {
    if (!whiteList.includes(to.path)) {
      await getShopInfo();
    }
    if (to.path === "/") {
      if (userStore.userInfo.shopInfoVo) {
        next({ path: "/overview" });
      } else {
        next({ path: "/console" });
      }
    } else {
      next();
    }
  }
}

export async function beforeCallBackasync(to: Route, from: Route, next: any) {
  const now = new Date().getTime();
  const token = storage.get("token");
  Vue.prototype.$Progress.start();
  timer.set();
  if (
    storage.get("token") &&
    storage.get("time") &&
    now - storage.get("time") < 1000 * 60 * 60 * 2
  ) {
    await hasToken(to, from, next, token);
  } else {
    /* has no token or not validate*/
    modifySignStatus("", null);
    logout();
    if (whiteList.includes(to.path)) {
      // 在免登录白名单，直接进入
      next();
    } else {
      if (process.env.NODE_ENV === "production") {
        const response = await getSystemConfig();
        const { code, data } = response;
        if (code === 200) {
          if (data.systemConfig && data.systemConfig.consoleUrl) {
            open(`${data.systemConfig.consoleUrl}`, "_top");
          }
        }
      } else {
        next(`/sign?redirect=${to.path}`);
      }
    }
  }
}

export async function afterCallBackasync(to: Route) {
  if (to.meta) {
    document.title = to.meta.title || config.title;
    router.app.$nextTick(() => {
      const container = document.querySelector(".admin");
      if (container) {
        container.scrollTo({ top: 0 });
      }
    });
    timer.clear();
    Vue.prototype.$Progress.finish();
  }
}
