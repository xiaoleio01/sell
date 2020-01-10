package com.shawlen.sell.controller;

import com.shawlen.sell.config.ProjectUrlConfig;
import com.shawlen.sell.constant.CookieConstant;
import com.shawlen.sell.constant.RedisConstant;
import com.shawlen.sell.dataobject.SellerInfo;
import com.shawlen.sell.enums.ResultEnum;
import com.shawlen.sell.service.SellerService;
import com.shawlen.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ShawLen
 * @date 2019/11/19 9:51 下午
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid, Map<String,Object> map, HttpServletResponse response){
        //1. openid去和数据库比对
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url","/sell/order/list");
            return new ModelAndView("common/error",map);
        }

        //2. 设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;

        //参数1是key，参数2是value，参数3是过期时间，参数4是时间单位
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);

        //3. 设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);

        return new ModelAndView("redirect:"+projectUrlConfig.getSell()+"/sell/seller/order/list");

    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,HttpServletResponse response,Map<String,Object> map){
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie != null){
            //2. 清掉redis
            //根据key清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));

            //3. 清掉cookie
            //就是设置一个空的token,时间设置为0
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");

        return new ModelAndView("common/success",map);
    }
}
