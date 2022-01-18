package com.medusa.gruul.platform.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medusa.gruul.common.core.constant.CommonConstants;
import com.medusa.gruul.common.core.exception.ServiceException;
import com.medusa.gruul.platform.api.entity.PlatformShopInfo;
import com.medusa.gruul.platform.api.entity.PlatformShopMessage;
import com.medusa.gruul.platform.api.entity.PlatformShopTemplateDetail;
import com.medusa.gruul.platform.api.model.dto.SubscribeMsgSendDto;
import com.medusa.gruul.platform.api.model.vo.ShopMessageDetailVo;
import com.medusa.gruul.platform.api.model.vo.ShopMessageVo;
import com.medusa.gruul.platform.mapper.PlatformShopMessageMapper;
import com.medusa.gruul.platform.model.dto.MotifyMsgStateDto;
import com.medusa.gruul.platform.service.*;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 店铺消息配置 服务实现类
 * </p>
 *
 * @author whh
 * @since 2020-05-22
 */
@Service
@Log4j2
public class PlatformShopMessageServiceImpl extends ServiceImpl<PlatformShopMessageMapper, PlatformShopMessage> implements IPlatformShopMessageService {

    @Autowired
    private IPlatformShopTemplateDetailService platformShopTemplateDetailService;
    @Autowired
    private IPlatformShopInfoService platformShopInfoService;

    @Override
    public List<ShopMessageVo> msgAll() {
        PlatformShopInfo info = platformShopInfoService.getInfo();
        PlatformShopTemplateDetail templateDetail = platformShopTemplateDetailService.getById(info.getShopTemplateDetailId());
        List<PlatformShopMessage> list = this.baseMapper.selectList(new QueryWrapper<PlatformShopMessage>()
                .eq("version", templateDetail.getVersion())
                .eq("use_type", CommonConstants.NUMBER_ONE));
        if (CollectionUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        Map<Integer, List<PlatformShopMessage>> listMap = list.stream().collect(Collectors.groupingBy(PlatformShopMessage::getMessageType));
        List<ShopMessageVo> vos = new LinkedList<>();
        //获取订单消息
        getVos(listMap.get(CommonConstants.NUMBER_ONE), vos, "订单消息");
        //获取用户消息
        getVos(listMap.get(CommonConstants.NUMBER_THREE), vos, "用户消息");
        return vos;
    }

    /**
     * 发送订阅消息
     *
     * @param msgSendDto com.medusa.gruul.platform.api.model.dto.SubscribeMsgSendDto
     */
    @Override
    public void subscribeMsgSend(SubscribeMsgSendDto msgSendDto) {

    }

    /**
     * 封装数据
     *
     * @param platformShopMessages 指定类型数据
     * @param list                 数组
     * @param title                标题
     */
    private void getVos(List<PlatformShopMessage> platformShopMessages, List<ShopMessageVo> list, String title) {
        if (CollectionUtil.isEmpty(platformShopMessages)) {
            return;
        }
        ShopMessageVo vo = new ShopMessageVo();
        vo.setMsgTitle(title);
        List<ShopMessageDetailVo> vos = new LinkedList<>();
        for (PlatformShopMessage platformShopMessage : platformShopMessages) {
            ShopMessageDetailVo shopMessageDetailVo = BeanUtil.toBean(platformShopMessage, ShopMessageDetailVo.class);
            vos.add(shopMessageDetailVo);
        }
        vo.setShopMessageDetailVos(vos);
        list.add(vo);
    }




    @Override
    public void modifyState(MotifyMsgStateDto msgStateDto) {
        PlatformShopMessage shopMessage = this.getById(msgStateDto.getId());
        if (shopMessage == null) {
            throw new ServiceException("不存在指定消息");
        }
        if (msgStateDto.getMiniOpen() != null && msgStateDto.getMiniOpen() > 0) {
            String miniTemplateId = shopMessage.getMiniTemplateId();
            if (StrUtil.isEmpty(miniTemplateId)) {
                throw new ServiceException("请上传审核小程序之后再开启");
            }
        }
        PlatformShopMessage platformShopMessage = BeanUtil.toBean(msgStateDto, PlatformShopMessage.class);
        this.updateById(platformShopMessage);
    }



}
