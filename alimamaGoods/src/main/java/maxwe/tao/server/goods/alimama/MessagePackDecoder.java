package maxwe.tao.server.goods.alimama;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Pengwei Ding on 2017-04-14 16:47.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class MessagePackDecoder extends MessageToMessageDecoder<ByteBuf>{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {

//        final byte[] array;
//        final int length = msg.readableBytes();
//        array = new byte[length];
//        msg.getBytes(msg.readerIndex(), array, 0, length);
//        MessagePack msgPack = new MessagePack();
//        out.add(msgPack.read(array));

        out.add(msg.toString(Charset.forName("UTF-8")));
    }
}
