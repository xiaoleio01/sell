package com.shawlen.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 微信账号配置
 * @author ShawLen
 * @date 2019/11/6 4:33 下午
 */
@Component
@Data
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    /**
     * 公众号密钥
     */
    private String mpAppSecret;

    /**
     * 测试登陆的商户号
     */
    private String mpAppIdLogin;

    /**
     * 开放平台密钥
     */
    private String openAppSecret;

    /**
     * 开放平台appid号
     */
    private String openAppIdLogin;

    /**
     * 测试支付的商户号
     */
    private String mpAppIdPay;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 模版id
     */
    private Map<String,String> templateId;




    /**
     * 商户证书路径
     */
    private String keyPath;

    /**
     *
     */
    private String mchId;

    /**
     * 异步通知地址
     */
    private String notifyUrl;

}
