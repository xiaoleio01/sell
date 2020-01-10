package com.shawlen.sell.utils;

import com.shawlen.sell.vo.ResultVO;

/**
 * @author ShawLen
 * @date 2019/10/31 10:04 上午
 */
public class ResultVOUtil {

    public  static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO fail(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
