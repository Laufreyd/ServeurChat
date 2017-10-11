/**
 * 
 */
package com.Laufreyd.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author User
 *
 */
public class NetClient implements Runnable {
	
	private final Socket socket;
	private final Server server;
	private final Thread thread;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private boolean isRunning = true;
	
	public NetClient(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
		
		try {
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			this.out.flush();
			
			this.in = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.thread = new Thread(this);
		this.thread.start();
	}

	@Override
	public void run() {
		
		while(this.isRunning) {
			
			try {
			String message = this.receiveMessage();
			
			for (NetClient client : this.server.CacheClients.values()) {
				if(client.getIp() == "127.0.0.1") {
					client.sendMessage(message);
				}
			}
			} catch (Exception e) {
				this.isRunning = false;
				this.server.CacheClients.remove(this.getIp());
				this.close();
			}
		}
	}
	
	private String receiveMessage() {
		String message = null;
		
		try {
			message = (String) this.in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		return message;
	}
	
	public String getIp() {
		return this.socket.getInetAddress().getHostAddress();
	}
	
	private void sendMessage(String message) {
		try {
			this.out.writeObject(message);
			this.out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void close() {
		try {
			if(this.getIp() == "127.0.0.1") {
				//TODO Prévenir les clients
				this.server.killServer();
			}
			else {
				this.in.close();
				this.out.close();
				this.socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
