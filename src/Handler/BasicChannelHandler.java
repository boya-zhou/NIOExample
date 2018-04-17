package Handler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by boyazhou on 4/7/18.
 */

interface TransformHandler<T>{
     T transform(T t);
}

public class BasicChannelHandler implements Handler<SocketChannel> {


    @Override
    public void handle(SocketChannel s) throws IOException {

        //when related, it must got a buffer
        ByteBuffer buffer = ByteBuffer.allocateDirect(8080);
        int read = s.read(buffer);

        if (read == -1){
            s.close();
            return;
        }

        if (read > 0){

            // http://tutorials.jenkov.com/java-nio/buffers.html
            //capacity do not change, pos limit depends on write or read mode of buffer

            // pos = 0, limit = 80, capacity = 80
            // After read "hello\n" pos = 6, limit = 80, capacity = 80

            buffer.flip();

            TransformHandler<Integer> th = (ch) -> Character.isLetter(ch) ? (ch ^ ' '): ch;

            // after flip the pos = 0, limit = 6, capacity = 80
            for (int i = 0; i <buffer.limit(); i++){
                Byte newByte = th.transform(Byte.toUnsignedInt(buffer.get(i))).byteValue();
                buffer.put(i, newByte);
            }

            // check if there is byte between pos and limit
            while(buffer.hasRemaining()){
                s.write(buffer);
            }

            buffer.compact();
        }


    }
}
