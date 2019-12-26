package com.lanxin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanxin.bean.Product;
import com.lanxin.dao.ProductMapper;

@Service
public  class ProductServiceImpl implements ProductService{
     @Autowired
     private ProductMapper productMapper;
    
     public List<Product> selectAllProduct(Integer page,Integer count){
    	 List<Product> productList=productMapper.selectAllProduct(page,count);
    	 return productList;
     }
     public Integer selectAllCount() {
    	 Integer total=productMapper.selectAllCount();
    	 return total;
     }

	
}
