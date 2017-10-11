/**
 * 
 */
package com.Laufreyd.Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author User
 *
 */
public class Client {
	
	private HashMap<String, Socket> sockets = new HashMap<String, Socket>();
	
	private boolean isRunning = true;
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private Thread thread;
	
	public Client() {
		
		this.init();
		
		while(isRunning) {
			this.sendMessage();
		}
		
	}
	
	public void init() {
		this.out = new ObjectOutputStream(getOutputStream());
	}
	
	public void sendMessage() {
		
	}

}
