package com.lanxin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lanxin.bean.Product;
@Mapper
public interface ProductMapper {
	int insertProduct(Product record);
	int updateProductByName(@Param("name")String name,@Param("imgurl")String imgurl);
  
	List<Product> selectAllProduct(@Param("page") Integer page,@Param("count") Integer count);
	int selectAllCount();
}
