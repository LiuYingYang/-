package com.medusa.gruul.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medusa.gruul.platform.api.entity.PlatformShopMessage;
import com.medusa.gruul.platform.api.model.dto.SubscribeMsgSendDto;
import com.medusa.gruul.platform.api.model.vo.MiniMsgVo;
import com.medusa.gruul.platform.api.model.vo.ShopMessageVo;
import com.medusa.gruul.platform.model.dto.MotifyMsgStateDto;

import java.util.List;

/**
 * <p>
 * 店铺消息配置 服务类
 * </p>
 *
 * @author whh
 * @since 2020-05-22
 */
public interface IPlatformShopMessageService extends IService<PlatformShopMessage> {

    /**
     * 获取店铺消息
     *
     * @return com.medusa.gruul.platform.api.model.vo.ShopMessageVo
     */
    List<ShopMessageVo> msgAll();

    /**
     * 发送订阅消息
     *
     * @param msgSendDto com.medusa.gruul.platform.api.model.dto.SubscribeMsgSendDto
     */
    void subscribeMsgSend(SubscribeMsgSendDto msgSendDto);

    /**
     * 修改消息状态
     *
     * @param msgStateDto com.medusa.gruul.platform.model.dto.MotifyMsgStateDto
     */
    void modifyState(MotifyMsgStateDto msgStateDto);

}
