package com.shawlen.sell.handler;

import com.shawlen.sell.config.ProjectUrlConfig;
import com.shawlen.sell.exception.SellException;
import com.shawlen.sell.exception.SellerAuthorizeException;
import com.shawlen.sell.utils.ResultVOUtil;
import com.shawlen.sell.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ShawLen
 * @date 2019/11/20 11:04 上午
 */
@ControllerAdvice
public class SellerExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * 拦截SellerAuthorizeException的登录异常
     */
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        StringBuffer sb = new StringBuffer();
        sb.append("https://open.weixin.qq.com/connect/qrconnect");
        sb.append("?appid=wx6ad144e54af67d87");
        sb.append("&redirect_uri=http%3A%2F%2Fsell.springboot.cn%2Fsell%2Fqr%2FoTgZpwfGSu4WlqycCYtuk1AKTm18");
        sb.append("&response_type=code");
        sb.append("&scope=snsapi_login");
        sb.append("&state=http%3a%2f%2fshawlen.natapp1.cc%2fsell%2fwechat%2fqrUserInfo");
        //异常让他跳到登录地址去:暂时先用租用地址。 TODO
        return new ModelAndView("redirect:".concat(sb.toString()));
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO<SellException> handlerSellException(SellException e){
        return ResultVOUtil.fail(e.getCode(),e.getMessage());
    }
}
