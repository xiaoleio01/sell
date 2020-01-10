package com.shawlen.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信接受
 * @author ShawLen
 * @date 2019/11/6 11:20 上午
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
        log.info("进入auth方法。。。。code={}",code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxb328b84b6479920b&secret=d3ea07a324a0805d294cab3fe648e6a2" +
                "&code="+code+"&grant_type=authorization_code";

    }
}
