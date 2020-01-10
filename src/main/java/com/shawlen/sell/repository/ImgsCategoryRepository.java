package com.shawlen.sell.repository;

import com.shawlen.sell.dataobject.ImgsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ShawLen
 * @date 2019/12/9 9:52 下午
 */
public interface ImgsCategoryRepository extends JpaRepository<ImgsCategory,Integer>{

    List<ImgsCategory> findByProductId(String productId);

    List<ImgsCategory> findByImgCategory(String imgCategory);
}
