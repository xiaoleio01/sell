package com.shawlen.sell.controller;

import com.shawlen.sell.dataobject.ProductCategory;
import com.shawlen.sell.dataobject.ProductInfo;
import com.shawlen.sell.enums.ResultEnum;
import com.shawlen.sell.exception.SellException;
import com.shawlen.sell.form.ProductForm;
import com.shawlen.sell.service.CategoryServie;
import com.shawlen.sell.service.ProductInfoService;
import com.shawlen.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
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
 * 卖家端商品列表
 * @author ShawLen
 * @date 2019/11/14 3:59 下午
 */
@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryServie categoryServie;

    /**
     * 商品列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView productList(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
                                    Map<String,Object> map){
        PageRequest pageRequest = new PageRequest(page - 1,size);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(pageRequest);

        map.put("productInfoPage",productInfoPage);
        map.put("size",size);
        //当前页
        map.put("currentPage",page);

        return new ModelAndView("product/list",map);
    }

    /**
     * 下架
     */
    @RequestMapping("/off_sale")
    public ModelAndView down(@RequestParam("productId") String productId,Map<String,Object> map){
        try {
            productInfoService.down(productId);
        } catch (Exception e) {
            log.error("【卖家下架商品】发生异常");
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    /**
     * 上架
     */
    @RequestMapping("/on_sale")
    public ModelAndView up(@RequestParam("productId") String productId,Map<String,Object> map) {
        try {
            productInfoService.up(productId);
        } catch (Exception e) {
            log.error("【卖家上架商品】发生异常");
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);

    }

    /**
     * 卖家读取商品
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId,Map<String,Object> map){

        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productInfoService.findOne(productId);
            map.put("productInfo",productInfo);
        }
        //查询所有类目
        List<ProductCategory> categoryList = categoryServie.findAll();
        map.put("categoryList",categoryList);

        return new ModelAndView("product/index",map);
    }

    /**
     * 保存和更新
     * @param productForm   form提交表单实体
     * @param bindingResult
     * @param map
     * @return
     *
     *
     *  * @Cacheable这个注解是缓存redis。将商品缓存到redis中，如果redis中有内容就不会执行下面的方法。
     *      * 和@Cacheput不通，cacheput会每次都执行方法里，然后再放入redis中.
     *      * @CacheEvict则会清除redis里的缓存再执行里面的方法
     */
    @PostMapping("/save")
    @CacheEvict(cacheNames = "productInfo",key = "123")
    public ModelAndView save(@Valid ProductForm productForm, BindingResult bindingResult,Map<String,Object> map){
       //判断传过来参数有没有错误
        if(bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        ProductInfo productInfo = new ProductInfo();
        try {
            //如果ProductId不为空，说明是修改。
            if(!StringUtils.isEmpty(productForm.getProductId())){
                productInfo = productInfoService.findOne(productForm.getProductId());
            }else{
                productForm.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(productForm,productInfo);
            productInfoService.save(productInfo);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

}
