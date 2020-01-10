package com.shawlen.sell.controller;

import com.shawlen.sell.dataobject.ImgsCategory;
import com.shawlen.sell.dataobject.ProductCategory;
import com.shawlen.sell.dataobject.ProductInfo;
import com.shawlen.sell.service.CategoryServie;
import com.shawlen.sell.service.ImgsCategoryService;
import com.shawlen.sell.service.ProductInfoService;
import com.shawlen.sell.utils.ResultVOUtil;
import com.shawlen.sell.vo.ProductInfoVO;
import com.shawlen.sell.vo.ProductVO;
import com.shawlen.sell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryServie categoryServie;

    @Autowired
    private ImgsCategoryService imgsCategoryService;

    /**
     * 买家商品list
     *
     * @Cacheable这个注解是缓存redis。将商品缓存到redis中，如果redis中有内容就不会执行下面的方法。
     * 和@Cacheput不通，cacheput会每次都执行方法里，然后再放入redis中.
     * @CacheEvict则会清除redis里的缓存再执行里面的方法
     *
     *
     *
     * @return
     */
    //   @Cacheable(cacheNames = "productInfo",key = "123")
    @GetMapping("/list")
    public ResultVO list(){
        //查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        //查询类目（一次性查询）
//           List<Integer> categoryTypeList = new ArrayList<Integer>();
//           //传统方法
//           for (ProductInfo productInfo : productInfoList){
//                categoryTypeList.add(productInfo.getCategoryType());
//           }
        //精简方法(java8, lambda)
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryServie.findByCategoryTypeIn(categoryTypeList);

        //3. 数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory: productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo: productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }


    /**
     * 根据类目获取商品数据
     * @return
     */
    @PostMapping("/category")
    public ResultVO category(@RequestParam("category") String category){
        System.out.println(category);
        //查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        //3. 数据拼装
        List<ProductInfoVO> productInfoVOList = new ArrayList<>();
        for (ProductInfo productInfo: productInfoList) {
            if (productInfo.getCategoryType().toString().equals(category)) {
                ProductInfoVO productInfoVO = new ProductInfoVO();
                BeanUtils.copyProperties(productInfo, productInfoVO);
                productInfoVOList.add(productInfoVO);
            }
        }
        return ResultVOUtil.success(productInfoVOList);
    }
}
