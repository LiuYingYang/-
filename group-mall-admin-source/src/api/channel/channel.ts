/*
 * @description: 抽离开源版本
 * @Author: chuyinlong
 * @Date: 2021-08-20 17:29:06
 * @LastEditors: chuyinlong
 * @LastEditTime: 2021-08-26 09:47:30
 */
import api from "@/libs/request";

/**
 * 公众号配置添加或修改
 * @param data Dto
 */
export const modifyMpConfig = (data: any) => {
  return api.post(`/platform-open/mini-mp-conf`, data, {
    headers: {
      "Content-Type": "application/json",
    },
  });
};

// 更新小程序数据
export const updateMini = (data: any) => {
  return api.put("/platform-open/mini-info/update", data, {
    headers: { "Content-Type": "application/json" },
  });
};
