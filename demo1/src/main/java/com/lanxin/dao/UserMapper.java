package com.lanxin.dao;

import com.lanxin.bean.User;
import com.lanxin.bean.UserExample;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User selectByParam(@Param("username")String username,@Param("password") String password);
    User selectByPJ(User record);
    User selectByMap(Map<String,String> params);
    User selectByCollection(Collection<String> collection); 
    List<User> selectByArray(Integer[] ids);
}