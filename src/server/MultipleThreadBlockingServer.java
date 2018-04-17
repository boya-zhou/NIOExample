package server;

import Handler.BasicHandler;
import Handler.MultipleThreadHandler;
import Handler.PrintHandler;
import Handler.UncheckedErrorHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MultipleThreadBlockingServer {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(8081);
		
		while(true) {
			Socket socket = serverSocket.accept();
			new MultipleThreadHandler(new UncheckedErrorHandler<Socket>(new PrintHandler<Socket>(new BasicHandler()))).handle(socket);
		}
	}
}
