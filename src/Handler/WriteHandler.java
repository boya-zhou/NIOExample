package Handler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

/**
 * Created by boyazhou on 4/8/18.
 */
public class WriteHandler implements Handler<SelectionKey>{

    private final Map<SocketChannel, Queue<ByteBuffer>> pendingData;

    public WriteHandler(Map<SocketChannel, Queue<ByteBuffer>> pendingData){
        this.pendingData = pendingData;
    }

    @Override
    public void handle(SelectionKey selectionKey) throws IOException {
        SocketChannel sc = (SocketChannel) selectionKey.channel();

        Queue<ByteBuffer> queue = pendingData.getOrDefault(sc, new ArrayDeque<>());

        while(!queue.isEmpty()){
            ByteBuffer buffer = queue.peek();
            int written = sc.write(buffer);
            if (written == -1){
                sc.close();
                pendingData.remove(sc);
                return;
            }

            if (buffer.hasRemaining()) return;
            queue.remove();
        }

        selectionKey.interestOps(SelectionKey.OP_READ);
    }
}
