package com.shawlen.sell.form;

import lombok.Data;

/**
 * @author ShawLen
 * @date 2019/11/15 3:14 下午
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    /** 类目名字 */
    private String categoryName;

    /** 类目编号*/
    private Integer categoryType;

}
