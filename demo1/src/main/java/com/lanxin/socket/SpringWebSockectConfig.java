package com.lanxin.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SpringWebSockectConfig implements WebSocketConfigurer{
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(),"/websocket/socketServer")
                .addInterceptors(new SpringWebSocketHandlerInterceptor());
        
        registry.addHandler(webSocketHandler(), "/sockjs/socketServer")
                .addInterceptors(new SpringWebSocketHandlerInterceptor()).withSockJS();
    }
 
    @Bean
    public TextWebSocketHandler webSocketHandler(){
 
        return new SpringWebSocketHandler();
    }



}
