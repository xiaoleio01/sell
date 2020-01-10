package com.shawlen.sell.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ShawLen
 * @date 2019/11/19 9:14 下午
 */
@Data
@ConfigurationProperties(prefix = "projectUrl")
@Component
public class ProjectUrlConfig {

    /**
     * 微信公众平台授权URL
     */
    public String wechatMpAuthorize;

    /**
     * 微信开放平台授权URL
     */
    public String wechatOpenAuthorize;

    /**
     * 点餐系统
     */
    public String sell;

}
