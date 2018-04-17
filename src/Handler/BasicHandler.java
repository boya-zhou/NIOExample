package Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class BasicHandler implements Handler<Socket>{


	public void handle(Socket socket) throws IOException{
		try(
				Socket s = socket;
				InputStream in = s.getInputStream();
				OutputStream out = s.getOutputStream()
			){
				int data;
				while((data = in.read()) != -1) {
					if (data == '%') throw new IOException("% appears!");
					out.write(data);
				}
			}
	}
}
