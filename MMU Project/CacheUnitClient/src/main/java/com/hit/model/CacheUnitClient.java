package com.hit.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class CacheUnitClient {
	Socket socket;
	
	public String send(String s) {
		//Connect to Server
		try {
			socket = new Socket("localhost", 12345);
			
			ObjectInputStream inFromServer=new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream toServer=new ObjectOutputStream(socket.getOutputStream());
			
			toServer.writeObject(s);
			String answer=(String) inFromServer.readObject();
			return answer;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
