package Handler;

import java.io.IOException;

public abstract class DecoratedHandler<S> implements Handler<S>{
	private final Handler<S> handler;
	
	protected DecoratedHandler(Handler<S> handler) {
		this.handler = handler;
	}
	
	public void handle(S s) throws IOException {
		handler.handle(s);
	}
}
