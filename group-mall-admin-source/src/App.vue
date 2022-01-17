<!--
 * @description: 
 * @Author: vikingShip
 * @Date: 2021-07-17 13:29:51
 * @LastEditors: vikingShip
 * @LastEditTime: 2021-09-07 14:50:46
-->
<template>
  <div id="app">
    <vue-progress-bar />
    <router-view />
  </div>
</template>

<script lang="ts">
import { Vue, Component } from "vue-property-decorator";
@Component
export default class App extends Vue {
  /** 播报计时器 */
  timer: number | null = null;

  mounted() {
    const animationId = document.getElementById("animationLoad") as HTMLElement;
    setTimeout(() => {
      animationId.style.display = "none";
    }, 200);
    this.$STORE.globalStore.initSocket();
  }

  clearTimer() {
    clearInterval(Number(this.timer));
    this.timer = null;
  }

  /**
   * 轮询
   */

  training() {
    const userInfo = window.localStorage.getItem("userInfo") as string;
    return new Promise((resolve) => {
      try {
        const { shopInfoVo } = JSON.parse(userInfo);
        this.timer = setInterval(() => {
          clearInterval(Number(this.timer));
          resolve();
        }, 1000);
      } catch (err) {
        return null;
      }
    });
  }
}
</script>

