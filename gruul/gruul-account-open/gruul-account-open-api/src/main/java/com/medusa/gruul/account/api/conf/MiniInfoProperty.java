package com.medusa.gruul.account.api.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author whh
 * @description
 * @data: 2019/11/26
 */
@Component
@ConfigurationProperties(prefix = "info")
@Data
public class MiniInfoProperty {

    /**
     * appId
     */
    private String appId;

    /**
     * secret
     */
    private String secret;
}
