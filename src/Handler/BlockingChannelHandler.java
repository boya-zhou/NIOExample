package Handler;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Created by boyazhou on 4/8/18.
 */
public class BlockingChannelHandler extends DecoratedHandler<SocketChannel> {

    public BlockingChannelHandler(Handler<SocketChannel> bh){
        super(bh);

    }
    @Override
    public void handle(SocketChannel socketChannel) throws IOException {
        while(socketChannel.isConnected()){
            super.handle(socketChannel);
        }
    }
}
