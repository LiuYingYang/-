/*
 * @description:
 * @Author: vikingShip
 * @Date: 2021-08-16 17:04:26
 * @LastEditors: vikingShip
 * @LastEditTime: 2021-09-06 13:41:33
 */
import { ApiSkuType, GoodDetailInfo } from "@/views/goods/marketModel/goodType";
/**
 * @LastEditors: vikingShip
 * @description: NewGoodSellInfo/state
 * @param radioMore 规格控制标识
 * @param showAttributes 是否展示产品属性
 * @param productSkuIndex 上传sku下标
 * @param unifyLimitNum 统一限购数量
 * @param normsList 规格列表
 * @param serviceAssuranceList 服务保障
 */
export interface NewGoodSellInfoState {
  radioMore: boolean;
  showAttributes: boolean;
  formModel: NewGoodSellFrom;
  isWeight: boolean;
  productSkuIndex: number;
  unifyLimitNum: number | string;
  normsList: ApiSkuType[];
  serviceAssuranceList: ServiceItem[];
}

type PickApiSkuType = Pick<
  ApiSkuType,
  "weight" | "originalPrice" | "price" | "stock" | "perLimit" | "productId"
>;

export interface NewGoodSellFrom
  extends Omit<GoodDetailInfo, "skuStocks">,
    PickApiSkuType {
  skuStocks: Array<Partial<ApiSkuType>>;
  [x:string]:any
}

/**
 * @LastEditors: vikingShip
 * @description: 服务保障列表的item
 * @param name 服务名称
 * @param state 状态
 */
interface ServiceItem {
  name: string;
  state: boolean;
  text: string;
}
