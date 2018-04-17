package Handler;

import java.io.IOException;
import java.io.UncheckedIOException;

public class UncheckedErrorHandler<S> extends DecoratedHandler<S>{

	public UncheckedErrorHandler(Handler<S> handler) {
		super(handler);
	}

	@Override
	public void handle(S socket) throws IOException {
		try {
			super.handle(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new UncheckedIOException(e);
		}		
	}

}
