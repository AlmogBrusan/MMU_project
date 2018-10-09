package com.hit.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;

public class HandleRequest<T> implements Runnable {

	private Socket s;
	private CacheUnitController<T> cont;
	public String answer;
	
	public HandleRequest(Socket s, CacheUnitController<T> controller) {
		this.s=s;
		this.cont=controller;
	}
	
	@Override
	public void run() {
		try {
			
			ObjectOutputStream toClient =new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream fromClient=new ObjectInputStream(s.getInputStream());
						
			String req=(String) fromClient.readObject();
		
			Type ref = new TypeToken<Request<DataModel<T>[]>>() {}.getType();
			Request<DataModel<T>[]> request = new Gson().fromJson(req, ref);
			
			boolean is_success = true;
			
			String option = request.getHeaders().get("action").toString().toUpperCase();
			switch(option) 
			
			{
			case "UPDATE":
				is_success=cont.update((DataModel<T>[]) request.getBody());
				answer="Action: "+option+",Result: "+is_success;
				toClient.writeObject(answer);
				break;
				
			case "GET":
				cont.get((DataModel<T>[]) request.getBody());
				answer="Action: "+option+",Result: "+is_success;
				toClient.writeObject(answer);
				break;
				
			case "DELETE":
				is_success=cont.delete((DataModel<T>[]) request.getBody());
				answer="Action: "+option+",Result: "+is_success;
				toClient.writeObject(answer);
				break;
				
			case "STATISTICS":
				answer="Action: "+option+",Result: "+is_success+",";
				answer+= cont.statistics();
				toClient.writeObject(answer);
				break;
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
