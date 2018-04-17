package Handler;

import java.io.IOException;

public class MultipleThreadHandler<S> extends UncheckedErrorHandler<S>{

	public MultipleThreadHandler(Handler<S> handler) {
		super(handler);
		// TODO Auto-generated constructor stub
	}
	
	public void handle(S socket) {
		new Thread(() -> {
			try {
				super.handle(socket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}
}
