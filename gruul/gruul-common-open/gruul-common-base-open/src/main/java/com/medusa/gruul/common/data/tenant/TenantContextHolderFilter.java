package com.medusa.gruul.common.data.tenant;

import com.medusa.gruul.common.core.constant.CommonConstants;
import com.medusa.gruul.common.redis.RedisManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantContextHolderFilter extends GenericFilterBean {
    @Override
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getHeader(CommonConstants.TOKEN);
        if (StringUtils.isNotBlank(token)) {
            RedisManager redisManager = RedisManager.getInstance();
            String user = redisManager.get(StringUtils.isBlank(token) ? CommonConstants.CUR_USER : token);
            CurUserContextHolder.setCurUser(user);
            log.debug("获取header中的token为:{}", token);
        }

        filterChain.doFilter(request, response);

    }
}
