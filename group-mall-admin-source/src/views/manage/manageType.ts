/*
 * @description:
 * @Author: vikingShip
 * @Date: 2021-08-26 13:42:49
 * @LastEditors: vikingShip
 * @LastEditTime: 2021-09-03 16:46:13
 */

export interface ManageState {
  dateValue: string[];
  dayQuery: ManageQueryType;
  orderQuery: any;
}
/**
 * @LastEditors: vikingShip
 * @description:
 * @param trade 每日交易详情
 * @param tradeLineVos 交易数组
 * @param yesterdayTrade 昨日交易详情
 */
export interface ManageQueryType {
  updateTime: string;
  trade: ManageTrade;
  tradeLineVos: Array<ManageTrade>;
  yesterdayTrade: ManageTrade;
}

/**
 * @LastEditors: vikingShip
 * @description:
 * @param date 日期
 * @param transactionVolume 交易量
 * @param turnover 交易额
 */
interface ManageTrade {
  date?: string;
  transactionVolume: number;
  turnover: number;
  views?: number;
}

export type ManageOrderQuery = Record<
  "shipped" | "waitForPay" | "waitForPickup" | "waitForSend",
  number
>;

export type ManageAccountQuery = Record<"pv" | "totalUv" | "uv", number>;

/**
 * @LastEditors: vikingShip
 * @description: 发票列表
 * @param accountId 商户id
 * @param defaultStatus 是否默认使用 0-否 1-是
 * @param headType 抬头类型：1个人或事业单位，2企业
 * @param invoiceRiseName 抬头名称
 * @param invoiceTaxpayerNum 纳税人识别号
 * @param remark 备注
 */
export interface BillListType {
  accountId: number;
  defaultStatus: number;
  headType: number;
  id: number;
  invoiceRiseName: string;
  invoiceTaxpayerNum: string;
  remark: string;
}
