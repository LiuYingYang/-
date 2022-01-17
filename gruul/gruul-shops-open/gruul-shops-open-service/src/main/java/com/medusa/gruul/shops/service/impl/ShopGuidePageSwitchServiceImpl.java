package com.medusa.gruul.shops.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medusa.gruul.shops.api.entity.ShopGuidePageSwitch;
import com.medusa.gruul.shops.mapper.ShopGuidePageSwitchMapper;
import com.medusa.gruul.shops.service.IShopGuidePageSwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: xiaoq
 * @Date : 2020/10/17 13:13
 */
@Service
public class ShopGuidePageSwitchServiceImpl extends ServiceImpl<ShopGuidePageSwitchMapper, ShopGuidePageSwitch>
		implements IShopGuidePageSwitchService {
	@Autowired
	private ShopGuidePageSwitchMapper shopGuidePageSwitchMapper;


	/**
	 * 获取引导页开关信息
	 * @return
	 */
	@Override
	public ShopGuidePageSwitch getShopGuidePageSwitch() {
		return shopGuidePageSwitchMapper.seleteByShopId();
	}

	/**
	 * 更改引导页开启关闭
	 * @param status 状态值
	 */
	@Override
	public void updateShopGuidePageSwitch(Boolean status) {
		shopGuidePageSwitchMapper.updateShopGuidePageSwitchStatus(status);
	}

	/**
	 * 生成默认店铺开关
	 * @param jsonStr
	 */
	@Override
	public void init(String jsonStr) {
		ShopGuidePageSwitch shopGuidePageSwitch = new ShopGuidePageSwitch();
		//初始化 t_shop_guide_page_switch
		shopGuidePageSwitch.setOpen(false);
		shopGuidePageSwitchMapper.insert(shopGuidePageSwitch);
	}

}
