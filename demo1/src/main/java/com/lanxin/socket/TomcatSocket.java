package com.lanxin.socket;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket/test")
public class TomcatSocket {
	@OnOpen
	public void onOpen(){
		System.out.println("WEBopen");
	}
	@OnClose
	public void onClose(){
		System.out.println("WEBCLOSE");
	}
	@OnMessage
	public void onMessage(final Session session,final String msg){
		System.out.println("send message"+msg);
		if(session.isOpen()){
			//将websocket传过来的值返回回去
			Runnable runnable = new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
					try {
						session.getBasicRemote().sendText(msg);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			};

			ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
			service.scheduleAtFixedRate(runnable, 60, 30, TimeUnit.SECONDS);
		}
	}

}
