package server;

import Handler.BasicHandler;
import Handler.PrintHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadVlockingServerWithTry{
	public static void main(String[] args) throws IOException {
		
		ServerSocket serverSocket = new ServerSocket(8080);
		while(true) {
			Socket socket = serverSocket.accept();
			System.out.println(socket.getPort());
			new PrintHandler(new BasicHandler()).handle(socket);
		}
	}
}
