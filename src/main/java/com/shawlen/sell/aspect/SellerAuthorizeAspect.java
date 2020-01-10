package com.shawlen.sell.aspect;

import com.shawlen.sell.constant.CookieConstant;
import com.shawlen.sell.constant.RedisConstant;
import com.shawlen.sell.exception.SellerAuthorizeException;
import com.shawlen.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 卖家授权验证AOP
 * @author ShawLen
 * @date 2019/11/20 10:41 上午
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //TODO 暂时注释AOP不做登录检查

    /**
     *
     * AOP切入点是com.shawlen.sell.controller里以Seller开头的Controller里的方法
     * 并且不是com.shawlen.sell.controller里的SellerUserController里的方法。

    @Pointcut("execution(public * com.shawlen.sell.controller.Seller*.*(..))"
            + "&& !execution(public * com.shawlen.sell.controller.SellerUserController.*(..)) ")
    public void verify(){

    }
     */

    /**
     * 在verify() 方法切入点之前做这个操作。

    @Before("verify()")
    public void doVerify(){
        //先获取request
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //2. 查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie == null){
            log.warn("【登录校验】Cookie中查不到token");
            throw new SellerAuthorizeException();
        }
        //3. 去redis里查
        String redisValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
       //   如果是空的
        if(StringUtils.isEmpty(redisValue)){
            log.warn("【登录校验】Redis中查不到token");
            throw new SellerAuthorizeException();
        }
    }
     */
}
