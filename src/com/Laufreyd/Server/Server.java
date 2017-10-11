/**
 * 
 */
package com.Laufreyd.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author User
 *
 */
public class Server {

		private ServerSocket serverSocket;
		private boolean isRunning = true;
		protected HashMap<String, NetClient> CacheClients = new HashMap<String, NetClient>();
		
		public Server() {
			
			try {
				this.serverSocket = new ServerSocket(8080);
				
				while(this.isRunning) {
					Socket ClientSocket = this.serverSocket.accept();
					
					NetClient client = new NetClient(ClientSocket, this);
					
					this.CacheClients.put(client.getIp(), client);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void killServer() {
			try {
				this.serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
