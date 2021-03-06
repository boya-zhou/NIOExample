package server;

import Handler.AcceptHandler;
import Handler.Handler;
import Handler.ReadHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * Created by boyazhou on 4/7/18.
 */
public class NonBlockingNIOServer {
    public static void main(String[] args) throws IOException{
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8008));
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        Map<SocketChannel, Queue<ByteBuffer>> pendingData = new HashMap<>();

        Handler<SelectionKey> acceptHandler = new AcceptHandler(pendingData);
        Handler<SelectionKey> readHandler = new ReadHandler(pendingData);
        Handler<SelectionKey> writeHandler = new ReadHandler(pendingData);

        Collection<SocketChannel> sockets = new ArrayList<>();

        while(true){
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            for (Iterator<SelectionKey> iterator = keys.iterator(); iterator.hasNext();){
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isValid()){
                    if (key.isAcceptable()){
                        acceptHandler.handle(key);
                    }else if (key.isReadable()){
                        readHandler.handle(key);
                    }else if (key.isWritable()){
                        writeHandler.handle(key);
                    }
                }
            }

        }
    }

}
