package server;

import Handler.BasicHandler;
import Handler.ExecutorServiceHandler;
import Handler.Handler;
import Handler.PrintHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class ExecutorServiceBlockingServer {
	public static void main(String[] args) throws IOException {
        ServerSocket sc = new ServerSocket(8080);

		Handler<Socket> handler =
				new ExecutorServiceHandler<Socket>(
						new PrintHandler<Socket>(
								new BasicHandler()), 
						Executors.newCachedThreadPool(), 
						(t, e) -> System.out.println("Uncaught" + t + "" + e));
		
		while(true) {
            Socket socket = sc.accept();
            handler.handle(socket);
		}
	}
}
