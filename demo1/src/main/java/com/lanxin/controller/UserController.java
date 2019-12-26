package com.lanxin.controller;

import java.util.ArrayList;

import java.util.Collection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lanxin.bean.User;
import com.lanxin.bean.UserExample;
import com.lanxin.dao.UserMapper;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserMapper userMapper;
//	get请求 查询所有的数据：不传递参数
	@RequestMapping(value = "/selectall")
	@ResponseBody
	public List<User> selectAlluser1() {
		List<User> list = userMapper.selectByExample(new UserExample());
		return list;

	}
// 传递单参数
	@RequestMapping(value = "/select")
	@ResponseBody
	public User selectUser(Integer id) {
		User user1 = userMapper.selectByPrimaryKey(id);
		return user1;
	}
//传递多个参数
	@RequestMapping(value="/selectByParam")
	@ResponseBody
	public User selectByParam(String username,String password) {
		User user2=userMapper.selectByParam(username,password);
		return user2;
	}
//用对象方式接收参数
	@RequestMapping(value="/selectByPJ")
    @ResponseBody
    public User selectByPJ(User user) {
    	User user3=userMapper.selectByPJ(user);
    	return user3;
    }
	//Map方式接收参数
	@RequestMapping(value="/selectByMap")
	@ResponseBody
	public User selectByMap(String username,String password) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("username",username);
		params.put("password",password);
		User user4=userMapper.selectByMap(params);
		return user4;
	}
  //collectin方式接收
	@RequestMapping(value="/selectByCollection")
	@ResponseBody
	public User SelectByCollection(String username,String password) {
		Collection<String> collection=new ArrayList<String>();
		collection.add(username);
		collection.add(password);
		User user5=userMapper.selectByCollection(collection);
		return user5;
	}
//array方式接收
	@RequestMapping(value="/selectByArray")
	@ResponseBody
	public List<User> SelectByArray(@RequestBody Integer[] ids) {
		List<User> list2=userMapper.selectByArray(ids);
		return  list2;
	}
	@RequestMapping(value = "/post")
	@ResponseBody
	public Map<String, Object> selectByPost(User requestPeople) {
		System.out.println(requestPeople);
		Integer id = requestPeople.getId();
		// 查找对应id的用户信息
		User people = userMapper.selectByPrimaryKey(id);
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("code","200");
		data.put("data", people);
		return data;
	}

//插入数据
	 @RequestMapping(value="/insert")
	 @ResponseBody 
	 public Integer InsertUser( User requestPeople) {
	       System.out.println(requestPeople);
	       Integer i=  userMapper.insert(requestPeople);
	       return i;
    }
//delete数据
	
	  @RequestMapping(value="/delete")
	  @ResponseBody 
	  public Map<String, Object> DeleteUser(@RequestBody User user) {
	         Integer id=user.getId();
	         Integer j=userMapper.deleteByPrimaryKey(id);
	         Map<String,Object> data = new HashMap<String,Object>();
	 		data.put("code","200");
	 		if(j==1) {
	 			data.put("data", "删除成功");
	 		}else {
	 			data.put("data", "不存在这条数据");
	 		}
	 		
	         
	       return data;
	  }
//update数据
	  @RequestMapping(value="/update")
	  @ResponseBody
	  public Map<String,Object>UpdateUser(@RequestBody User newUser){
		   Integer i=userMapper.updateByPrimaryKeySelective(newUser);
		   Map<String,Object> data = new HashMap<String,Object>();
	 		data.put("code","200");
	 		if(i==1) {
	 			data.put("data", "更新数据成功");
	 		}else {
	 			data.put("data", "更新数据失败");
	 		}
	 		return data;
	  }
	 

}
