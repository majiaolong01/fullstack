package com.lanxin.controller;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanxin.bean.User;
import com.lanxin.bean.UserExample;
import com.lanxin.dao.UserMapper;
import com.lanxin.security.*;
import com.lanxin.util.RedisUtil;

@Controller
public class LoginController {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RedisUtil redisUtil;
	String publicKeyStr;String privateKeyStr;
	@RequestMapping(value = "/publicKey")
	@ResponseBody
	public Map<String, Object> getPublicKey() throws Exception {
		KeyPair keyPair=RSAUtil.getKeyPair();
		//获取公钥，返回到前端
		publicKeyStr=RSAUtil.getPublicKey(keyPair);
		privateKeyStr=RSAUtil.getPrivateKey(keyPair);
		Map<String,Object> data = new HashMap<String,Object>();
		Map<String,Object> publicKeyObject = new HashMap<String,Object>();
		publicKeyObject.put("publicKeyStr", privateKeyStr);
		data.put("code",200);
		data.put("data",publicKeyObject);
		return data;
	}
	@RequestMapping(value = "/login")
	@ResponseBody
	public Map<String, Object> getUserInfo(HttpServletRequest request,@RequestBody User user) throws Exception {
		//String password=user.getPassword();
		//System.out.println(password);
		byte[] content=Base64.decodeBase64(user.getPassword());
		PrivateKey privateKey= RSAUtil.string2PrivateKey(privateKeyStr);
		byte[] privateDecrypt = RSAUtil.privateDecrypt(content, privateKey);
		System.out.println("解密后的明文: " + new String(privateDecrypt));
		String password=new String(privateDecrypt);
		String username=user.getUsername();
		//查询用户信息
		User list=userMapper.selectByParam(username,password);
		System.out.println("用户信息 " + list);
		String sessionId=request.getSession().getId();
		System.out.println(sessionId);
		
		
//		创建token并返回用户信息，保存token,sessionId,timeStamp
		Map<String,Object> data = new HashMap<String,Object>();
		if(list!=null) {
			long timeStamp=new Date().getTime();
			String accessToken=AccessToken.createToken(list.getUserId(),timeStamp);
			//查询旧的token并清除
			String oldToken=redisUtil.get(list.getUserId());
			if(oldToken!=null&&(oldToken!=accessToken)) {
				redisUtil.del(oldToken);
			   System.out.println("删掉旧的token");
			}
			Map<String,Object> Authentication =new HashMap<String,Object>();
			Authentication.put("sessionId", sessionId);
			Authentication.put("timeStamp", timeStamp);
			Authentication.put("user_id",list.getUserId());
			redisUtil.hmset(accessToken, Authentication);
			redisUtil.set(list.getUserId(),accessToken);
			/*
			 * System.out.println(isExit); Map<Object,Object>
			 * author=redisUtil.hmget(accessToken);
			 * System.out.println(author.get("sessionId"));
			 */
			data.put("code",200);
			data.put("accessToken", accessToken);
			data.put("message","登录成功");
			Map<String,Object> userData =new HashMap<String,Object>();
			userData.put("userId",list.getUserId());
			userData.put("username",list.getUsername());
			data.put("data",userData);
		}else {
			data.put("code",101);
			data.put("message","用户不存在！");
		}
		
		return data;
	}

}
