package com.lanxin.interceptor;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lanxin.util.RedisUtil;

public class TokenInterceptor implements HandlerInterceptor {
	
	
	/*
	 * public static RedisUtil redisUtil() { return new RedisUtil(); }
	 */

    
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
	   //执行token校验
		String access_token=request.getHeader("access_token");
		String session_id=request.getSession().getId();
		long timeStamp=new Date().getTime();
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		RedisUtil redisUtil = (RedisUtil) factory.getBean("redisUtil");
		boolean isValid= verfifyToken(access_token,session_id,timeStamp,redisUtil);
		if(isValid) {
			return true;
		}else {
			 response.reset();
			 response.setCharacterEncoding("UTF-8");
			 response.setContentType("application/json;charset=UTF-8");
			 PrintWriter pw = response.getWriter();
			 pw.write("{\"code\":401,\"message\":\"token失效，请重新登录\"}");
			 pw.flush();
			 pw.close();
			return false;
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}
	
	public static Boolean verfifyToken(String access_token,String session_id,long timeStamp,RedisUtil redisUtil) {
		if(redisUtil==null) return false;
    	boolean isExit = redisUtil.hasKey(access_token);
    	//&&author.get("sessionId")==session_id
    	Map<Object,Object> author=redisUtil.hmget(access_token);
    	if(isExit&&(timeStamp- Long.valueOf(String.valueOf(author.get("timeStamp")))<=30*1000*60)) {
    		Map<String,Object> Authentication =new HashMap<String,Object>();
			Authentication.put("sessionId", session_id);
			Authentication.put("timeStamp", timeStamp);
			Authentication.put("user_id",author.get("sessionId"));
			redisUtil.hmset(access_token, Authentication);
    	   	return true;
    	}else {
    		return false;
    	}
    	
		
    	
    }
	 

}
