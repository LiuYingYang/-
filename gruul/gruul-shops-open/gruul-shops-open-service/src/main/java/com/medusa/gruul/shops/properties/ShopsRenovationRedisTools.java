package com.medusa.gruul.shops.properties;


import com.medusa.gruul.common.redis.RedisVisitorBaseFacade;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * @author create by zq
 * @date created in 2019/11/06
 */
public class ShopsRenovationRedisTools extends RedisVisitorBaseFacade {

    public static final String KEY_BASE = "shops_renovation";

    public ShopsRenovationRedisTools() {
        this(KEY_BASE);
    }

    public ShopsRenovationRedisTools(String baseKey) {
        super(baseKey);
    }


    public void innerRemoveCache(String name) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != requestAttributes) {
            this.del(name);
        }

    }

}
