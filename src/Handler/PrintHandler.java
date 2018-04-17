package Handler;

import java.io.IOException;

public class PrintHandler<S> extends DecoratedHandler<S>{
		
	public PrintHandler(Handler<S> handler) {
		super(handler);
	}

	public void handle(S socket) throws IOException {
		System.out.println("Connected to " + socket);
		try {
			super.handle(socket);
		} finally {
			System.out.println("Disconnected from" + socket);
		}
	}

}
