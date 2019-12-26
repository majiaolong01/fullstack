package com.lanxin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.lanxin.socket.SpringWebSocketHandler;

@Controller
@RequestMapping(value = "/websocket")
public class WebsocketController {
	 
	/*
	 * @Bean//这个注解会从Spring容器拿出Bean public SpringWebSocketHandler infoHandler() {
	 * 
	 * return new SpringWebSocketHandler(); }
	 */
	 @RequestMapping("/login")
	 @ResponseBody
	    public  Map<String,Object> login(HttpServletRequest request, HttpServletResponse response) throws Exception {
	        String username = request.getParameter("username");
	        System.out.println(username+"登录");
	        if(request.getSession(false) != null) {
                System.out.println("每次登录成功改变SessionID！");
                //安全考量，每次登陆成功改变 Session ID，原理：原来的session注销，拷贝其属性建立新的session对象
            }
	        HttpSession session = request.getSession();
	        Map<String,Object> data = new HashMap<String,Object>();
	        data.put("code",200);
			data.put("data", "登录成功！"); 
			session.setAttribute("SESSION_USERNAME", username); //一般直接保存user实体
	        return data;
	    }
	 
	/*
	 * @RequestMapping("/send")
	 * 
	 * @ResponseBody public String send(HttpServletRequest request) { String
	 * username = request.getParameter("username");
	 * infoHandler().sendMessageToUser(username, new TextMessage("你好，测试！！！！"));
	 * return null; }
	 */

	

}
