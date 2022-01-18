package com.medusa.gruul.platform.constant;

/**
 * @author whh
 * @description
 * @data: 2019/11/26
 */
public class RedisConstant {

    /**
     * 平台redis前缀
     */
    public static final String PREFIX = "platform:";


    /**
     * 平台用户模块token key
     */
    public static final String TOKEN_KEY = "tk:";


    /**
     * 平台用户模块手机号校验
     */
    public static final String PHONE_KEY = "phone:";

    /**
     * 平台用户模块手机号校验成功有效期凭证key
     */
    public static final String PHONE_CERTIFICATE_KEY = "phone:certificate:";

    /**
     * 基础库key
     */
    public static final String BASE_LIBRARY_KEY = "base_library_key:";

    /**
     * 基础库服务key
     */
    public static final String BASE_LIBRARY_SERVICE_KEY = BASE_LIBRARY_KEY + "service:";


    /**
     * 公众号授权回调成功根据authCode换取信息
     */
    public static final String MP_NOTIFY_AUTH_CODE = "mp-notify:auth-code";


    /**
     * 店铺过期通知
     */
    public static final String SHOP_DUETIME_NOTIFY = "shop:shop-duetime-notify:";


}
