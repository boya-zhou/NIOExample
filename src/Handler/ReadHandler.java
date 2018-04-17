package Handler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Queue;

/**
 * Created by boyazhou on 4/8/18.
 */
public class ReadHandler implements Handler<SelectionKey>{

    private final Map<SocketChannel, Queue<ByteBuffer>> pendingData;

    public ReadHandler(Map<SocketChannel, Queue<ByteBuffer>> pendingData){
        this.pendingData = pendingData;
    }

    @Override
    public void handle(SelectionKey selectionKey) throws IOException {
        SocketChannel sc = (SocketChannel)selectionKey.channel();
        ByteBuffer buf = ByteBuffer.allocate(80);
        int read = sc.read(buf);

        if (read == -1){
            pendingData.remove(sc);
            sc.close();
            return;
        }

        if (read > 0){
            pendingData.get(sc).add(buf);
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }
}
