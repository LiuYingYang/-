package com.medusa.gruul.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medusa.gruul.common.core.monitor.MonitorServiceConfig;
import com.medusa.gruul.platform.api.entity.PlatformServiceInfo;

/**
 * <p>
 * 服务信息表 服务类
 * </p>
 *
 * @author alan
 * @since 2020-02-26
 */
public interface IPlatformServiceInfoService extends IService<PlatformServiceInfo> {

    /**
     * 心跳生成
     *
     * @param monitorServiceConfig com.medusa.gruul.common.core.monitor.MonitorServiceConfig
     */
    void baseWarehouse(MonitorServiceConfig monitorServiceConfig);



}
