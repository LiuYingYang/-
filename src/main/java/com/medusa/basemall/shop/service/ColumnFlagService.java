package com.medusa.basemall.shop.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.product.vo.ColumnFlagVo;
import com.medusa.basemall.shop.entity.ColumnFlag;

/**
 * @author Created by wx on 2018/06/04.
 */
public interface ColumnFlagService extends Service<ColumnFlag> {

	/***
	 * 根据appmodelId查询栏目开关
	 *
	 * @param appmodelId
	 * @return ColumnFlag
	 */
    ColumnFlag findByAppmodelId(String appmodelId);

	/***
	 * 操作首页分类导航开关
	 *
	 * @param columnFlagVo
	 * @return Boolean
	 */
	Boolean homePageFlag(ColumnFlagVo columnFlagVo);

	/***
	 * 操作店铺公告开关
	 *
	 * @param columnFlagVo
	 * @return Boolean
	 */
	Boolean noticeFlag(ColumnFlagVo columnFlagVo);

	/***
	 * 操作发现页开关
	 *
	 * @param columnFlagVo
	 * @return Boolean
	 */
	Boolean articleFlag(ColumnFlagVo columnFlagVo);

	/***
	 * 操作底部打标开关
	 *
	 * @param columnFlagVo
	 * @return Boolean
	 */
	Boolean footFlag(ColumnFlagVo columnFlagVo);

	/***
	 * 操作招收代理开关
	 *
	 * @param columnFlagVo
	 * @return Boolean
	 */
	Boolean proxyFlag(ColumnFlagVo columnFlagVo);

	/***
	 * 操作积分商城开关
	 *
	 * @param columnFlagVo
	 * @return Boolean
	 */
	Boolean integralShopFlag(ColumnFlagVo columnFlagVo);

	/***
	 * 操作会员储值开关
	 *
	 * @param columnFlagVo
	 * @return Boolean
	 */
	Boolean memberRechargeFlag(ColumnFlagVo columnFlagVo);

	/***
	 * 操作会员开关
	 *
	 * @param columnFlagVo
	 * @return Boolean
	 */
	Boolean memberFlag(ColumnFlagVo columnFlagVo);

	/***
	 * 操作搜索词开关
	 *
	 * @param columnFlagVo
	 * @return Boolean
	 */
	Boolean serarchFlag(ColumnFlagVo columnFlagVo);

	/***
	 * 操作店铺故事开关
	 *
	 * @param columnFlagVo
	 * @return Boolean
	 */
	Boolean shopinfoFlag(ColumnFlagVo columnFlagVo);
}
