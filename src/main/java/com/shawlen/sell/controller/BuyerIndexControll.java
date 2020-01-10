package com.shawlen.sell.controller;

import com.shawlen.sell.dataobject.ImgsCategory;
import com.shawlen.sell.service.ImgsCategoryService;
import com.shawlen.sell.utils.ResultVOUtil;
import com.shawlen.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ShawLen
 * @date 2019/12/10 8:36 下午
 */
@RestController
@RequestMapping("/buyer/index")
@Slf4j
public class BuyerIndexControll {

    @Autowired
    private ImgsCategoryService imgsCategoryService;


    @GetMapping("/imgUrls")
    public ResultVO getImgsUrl(){
        List<ImgsCategory> imgsCategory = imgsCategoryService.getRollImgs();
        return ResultVOUtil.success(imgsCategory);
    }
}
