package com.hit.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Scanner;

public class CLI extends Observable implements Runnable{
	private Scanner input;
	private PrintWriter output;
	
	
	public CLI(InputStream in, OutputStream out) {
		input = new Scanner(in);
		output = new PrintWriter(out);
	}

	public void write(String string) {
		output.println(string);
		output.flush();
	}

	@Override
	public void run() {
		String runCheck = null;
		
		while (true) {
			write("What would you like to do? (start/stop)");
			
			runCheck = input.nextLine();
			runCheck.toLowerCase();
			 
			if(runCheck.equals("start")){
				write("Starting server...");
				setChanged();
				notifyObservers(runCheck);
			}else if(runCheck.equals("stop")) {
				write("Shutdown server...");
				setChanged();
				notifyObservers(runCheck);
			}else write("Please enter 'start' or 'stop' only");
			
		}
	}

}
