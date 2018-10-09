package com.hit.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hit.services.CacheUnitController;

public class Server implements Observer{

	private ServerSocket server;
	private Boolean isUp;
	private Executor executor;
	private CacheUnitController<String> controller;
	
	public Server() {
		isUp = false;
		server = null;
		executor = Executors.newCachedThreadPool();
		controller =new CacheUnitController<String>();
	}
	
	@Override
	public void update(Observable arg0, Object runCheck) {
		if(runCheck.equals("start")) {
			isUp = true;
			Thread thread=new Thread(new Runnable() {
				
				@Override
				public void run() {
					start();
					
				}
			});
			thread.start();

		}
		else if (runCheck.equals("stop")) {
			isUp = false;
		}
		
	}
	
	public void start() {
		try {
			//Open Socket Server with port 12345
			 server = new ServerSocket(12345);
			 
			 while(isUp) {
				 Socket s=server.accept();
				 executor.execute(new HandleRequest<String>(s,controller));
			 }
			 //If server opened or failed
		} catch (Exception e) {
			((ExecutorService)executor).shutdown();
			System.out.println("failed! please check if the server is open or too long waiting");
		} finally {
			try {
				if(server != null)
					server.close();
			} catch (Exception e) {
				e.printStackTrace();
				((ExecutorService)executor).shutdown();
			}
		}
	}

}
