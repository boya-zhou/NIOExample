package Handler;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
//http://www.cnblogs.com/onlysun/p/4601430.html

public class ExecutorServiceHandler<S> extends DecoratedHandler<S>{

	private ExecutorService pool;
	private UncaughtExceptionHandler eUncaughtExceptionHandler;

	public ExecutorServiceHandler(Handler<S> handler,
								ExecutorService pool,
								Thread.UncaughtExceptionHandler eUncaughtExceptionHandler) {
		super(handler);
		this.pool = pool;
		this.eUncaughtExceptionHandler = eUncaughtExceptionHandler;
		// TODO Auto-generated constructor stub
	}

	// anonymous subclass override on instantiation
	public void handle(S socket) {

		pool.submit(new FutureTask(
						() ->{
								super.handle(socket);
								return null;
							}
					)
				{
					protected void setExceptiion(Throwable t) {
						eUncaughtExceptionHandler.uncaughtException(Thread.currentThread(), t);
					}			
				}

		);
	}
	
//	private class ExceptionFutureTask extends FutureTask<Object>{
//		
//		public ExceptionFutureTask(Callable<Object> callable) {
//			super(callable);
//			// TODO Auto-generated constructor stub
//		}
//
//		protected void setExceptiion(Throwable t) {
//			eUncaughtExceptionHandler.uncaughtException(Thread.currentThread(), t);
//		}
//	}
}
