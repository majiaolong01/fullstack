package com.lanxin.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lanxin.bean.Product;

public interface ProductService {
	List<Product> selectAllProduct(@Param("page") Integer page,@Param("count") Integer count);
	Integer selectAllCount();
}
