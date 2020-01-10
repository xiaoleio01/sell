package com.shawlen.sell.dataobject.mapper;

import com.shawlen.sell.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * MyBatis方式
 * @author ShawLen
 * @date 2019/11/20 4:44 下午
 */
public interface ProductCategoryMapper {

    /**
     * 使用Map方式
     * @param map
     * @return
     */
    @Insert("insert into product_category(category_name,category_type) value " +
            "(#{category_name, jdbcType=VARCHAR},#{category_type, jdbcType=INTEGER})")
    int insertByMap(Map<String,Object> map);

    /**
     * 使用对象方式
     * @param productCategory
     * @return
     */
    @Insert("insert into product_category(category_name,category_type) value " +
            "(#{categoryName, jdbcType=VARCHAR},#{categoryType, jdbcType=INTEGER})")
    int insertByObject(ProductCategory productCategory);

    /**
     * 查询需要加Results,里面column是表字段，property是写明对应实体变量。
     * @param categoryType
     * @return
     */
    @Select("select * from product_category where category_type = #{categoryType}")
    @Results({
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName"),
            @Result(column = "category_type",property = "categoryType")
    })
    ProductCategory findByCategoryType(Integer categoryType);

    @Select("select * from product_category where category_name = #{categoryName}")
    @Results({
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName"),
            @Result(column = "category_type",property = "categoryType")
    })
    List<ProductCategory> findByCategoryName(String name);


    @Update("update product_category set category_name=#{categoryName} where category_type = #{categoryType}")
    int updateBycategoryType(@Param("categoryName") String categoryNamr,@Param("categoryType") Integer categoryType);

    /**
     * 根据对象修改数据
     * @param productCategory
     * @return
     */
    @Update("update product_category set category_name=#{categoryName} where category_type = #{categoryType}")
    int updateByObject(ProductCategory productCategory);

    @Delete("delete from product_category where category_type = #{categoryType}")
    int deleteBycategoryType(Integer categoryType);


    /**
     * 使用XML配置文件形式
     * @param categoryType
     * @return
     */
    ProductCategory selectByCategoryType(Integer categoryType);
}
