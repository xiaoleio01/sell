package com.shawlen.sell.controller;

import com.shawlen.sell.dataobject.ProductCategory;
import com.shawlen.sell.dataobject.ProductInfo;
import com.shawlen.sell.form.CategoryForm;
import com.shawlen.sell.service.CategoryServie;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author ShawLen
 * @date 2019/11/15 2:39 下午
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryServie categoryServie;

    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> productCategoryList =categoryServie.findAll();

        map.put("productCategoryList",productCategoryList);
        return new ModelAndView("category/list",map);
    }

    /**
     * 修改新增
     * @param categoryId
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false) String categoryId,Map<String,Object> map){
        if(!StringUtils.isEmpty(categoryId)){
            ProductCategory productCategory = categoryServie.findOne(Integer.valueOf(categoryId));
            map.put("productCategory",productCategory);
        }
        return new ModelAndView("category/index",map);
    }

    /**
     * 保存类目数据
     * @param categoryForm  类目form提交的实体
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String, Object> map){
        //判断传过来参数有没有错误
        if(bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }

        ProductCategory productCategory = new ProductCategory();
        try {
            if(!StringUtils.isEmpty(categoryForm.getCategoryId())){
                productCategory = categoryServie.findOne(Integer.valueOf(categoryForm.getCategoryId()));
            }
            BeanUtils.copyProperties(categoryForm,productCategory);
            categoryServie.save(productCategory);
            categoryServie.save(productCategory);

        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/category/list");
            return new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);

    }



}
