package server;

import Handler.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executors;

/**
 * Created by boyazhou on 4/7/18.
 */
public class BlockingNIOServer {
    public static void main(String[] args) throws IOException{
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8008));

        Handler<SocketChannel> handler =
                new ExecutorServiceHandler<SocketChannel>(
                        new PrintHandler<SocketChannel>(
                                new BlockingChannelHandler(
                                        new BasicChannelHandler()
                                )

                        ),
                        Executors.newCachedThreadPool(),
                        (t, e) -> System.out.println("Uncaught" + t + "" + e));

        while(true){
            SocketChannel sc = ssc.accept();
            handler.handle(sc);
        }
    }

}
