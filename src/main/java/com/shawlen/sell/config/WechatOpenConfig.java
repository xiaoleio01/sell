package com.shawlen.sell.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutImageMessage;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author ShawLen
 * @date 2019/11/19 4:33 下午
 */
@Component
public class WechatOpenConfig {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Bean
    public WxMpService wxOpenService(){
        WxMpService wxOpenService = new WxMpServiceImpl();
        wxOpenService.setWxMpConfigStorage(wxOpenConfigStorege());
        return wxOpenService;
    }

    @Bean
    public WxMpConfigStorage wxOpenConfigStorege(){
        WxMpDefaultConfigImpl wxMpConfigStorage = new WxMpDefaultConfigImpl();
        wxMpConfigStorage.setSecret(wechatAccountConfig.getOpenAppSecret());
        wxMpConfigStorage.setAppId(wechatAccountConfig.getOpenAppIdLogin());
        return wxMpConfigStorage;
    }

}
