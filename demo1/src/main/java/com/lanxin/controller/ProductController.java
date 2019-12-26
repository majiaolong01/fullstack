package com.lanxin.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lanxin.bean.Product;
import com.lanxin.dao.ProductMapper;
import com.lanxin.service.ProductServiceImpl;
import com.lanxin.util.RedisUtil;




@Controller
@RequestMapping(value = "/product")
public class ProductController {
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductServiceImpl productService;
	@Autowired
	private RedisUtil redisUtil;
	//插入数据
		 @RequestMapping(value="/insert")
		 @ResponseBody 
		 public Map<String,Object> InsertUser( Product requestPeople) {
		       Integer i=  productMapper.insertProduct(requestPeople);
		       redisUtil.set("product", requestPeople);
		       Map<String,Object> data = new HashMap<String,Object>();
		       if(i==0) {
		    	   data.put("code","00");
					data.put("data", "插入产品失败！"); 
		       }else {
		    	    data.put("code","200");
					data.put("data", "插入产品成功！"); 
		       }
		      
		       return data;
	    }
		 
		 
		 @RequestMapping(value="/download")
		 @ResponseBody
		 public void  downloadFile(HttpServletRequest request,@RequestParam("filename") String filename,HttpServletResponse response) throws IOException{
	
			    String path = request.getSession().getServletContext().getRealPath("\\image")+"\\"+filename;  
	            //获取输入流  
	            @SuppressWarnings("resource")
				InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(path)));
	            //转码，免得文件名中文乱码  
	            filename = URLEncoder.encode(filename,"UTF-8");  
	            //设置文件下载头  
	            response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
	            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型    
	            response.setContentType("multipart/form-data");   
	            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
	            int len = 0;  
	            while((len = inputStream.read()) != -1){  
	                out.write(len);  
	                out.flush();  
	            }  
	            out.close();  
		 }
	//上传文件
		 @RequestMapping(value="/upload")
		 @ResponseBody
		 public Map<String,Object> UploadFile(HttpServletRequest request,@RequestParam("name") String name,@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException{
			 Map<String,Object> data = new HashMap<String,Object>();
			 if(!file.isEmpty()) {
				String path=request.getServletContext().getRealPath("/image/");
				System.out.println(path);
				//获取文件名
				String fileName= file.getOriginalFilename();
				File filePath=new File(path,fileName);
				//判断路径是否存在，不存在句创建一个
				if(!filePath.getParentFile().exists()) {
					filePath.getParentFile().mkdirs();
				}
				file.transferTo(new File(path + File.separator + fileName));
				productMapper.updateProductByName(name,path + File.separator + fileName);
				data.put("code",200);
				data.put("message","文件上传成功");
			}else {
				data.put("code",00);
				data.put("message","文件上传失败");	
			}
			 return data;
		 }
	//查询所有商品
		 @RequestMapping(value="/allproduct")
		 @ResponseBody
		 public Map<String,Object> SelectAllProduct( @RequestBody HashMap<?, ?>  param){
			 Integer page = (Integer)param.get("page");
			 Integer count = (Integer)param.get("current");
			 List<Product> list = productService.selectAllProduct(page-1,count);
			 Integer total=productService.selectAllCount();
			 Map<String,Object> data = new HashMap<String,Object>();
			 Map<String,Object> listObject=new HashMap<String,Object>();
			 listObject.put("list",list);
			 listObject.put("page",page);
			 listObject.put("total",total);
			 data.put("code",200);
			 data.put("data",listObject);
			 data.put("message","查询成功");
			 return data;
		 }
		
}
