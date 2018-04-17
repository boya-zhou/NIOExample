package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class SingleThreadBlockingServer {
	public static void main(String[] args) {
		try {
		
			System.out.println();
			ServerSocket serverSocket = new ServerSocket(8080);
			while(true) {
				Socket socket = serverSocket.accept();
				// a new socket will be created
				System.out.println(socket.getPort());
				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				int data;
				
				while ((data = in.read()) != -1) {
					out.write(data);
				}
				
				out.close();
				in.close();
				socket.close();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
