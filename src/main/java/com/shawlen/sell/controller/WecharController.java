package com.shawlen.sell.controller;

import com.shawlen.sell.config.ProjectUrlConfig;
import com.shawlen.sell.enums.ResultEnum;
import com.shawlen.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

/**
 * 微信
 * @author ShawLen
 * @date 2019/11/6 4:16 下午
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WecharController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig proJectUrlConfig;

    /**
     * 获取微信授权code，
     * @param returnUrl 接受code的url.
     * @return  跳转重定向至上面的returnUrl+CODE和state
     */
    @RequestMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        String url = proJectUrlConfig.getWechatMpAuthorize()+"/sell/wechat/userInfo";

        //调用接口前先配置wxMpService的WechatMpConfig
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        return "redirect:"+redirectUrl;
    }

    /**
     * 接收微信code并获取到openid
     * @param code  微信code
     * @param returnUrl state
     * @return
     */
    @RequestMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state")String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openid = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:" + returnUrl + "?openid=" + openid;
    }

    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl){
        String url = proJectUrlConfig.getWechatOpenAuthorize()+"/sell/wechat/qrUserInfo";
        String redirectUrl = wxOpenService.buildQrConnectUrl(url,WxConsts.QrConnectScope.SNSAPI_LOGIN,URLEncoder.encode(returnUrl));
        System.out.println(redirectUrl);
        return "redirect:"+redirectUrl;
    }

    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,
                             @RequestParam(value = "state",required = false)String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        log.info("wxMpOAuth2AccessToken={}",wxMpOAuth2AccessToken);
        String openid = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:/seller/login?openid=" + openid;
    }
}
