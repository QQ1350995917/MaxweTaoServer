package maxwe.tao.server.goods.alimama;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

/**
 * Created by Pengwei Ding on 2017-04-14 10:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class GoodsInitializer extends ChannelInitializer<SocketChannel> {
    private final SslContext sslContext;

    public GoodsInitializer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (this.sslContext != null) {
            pipeline.addLast(this.sslContext.newHandler(ch.alloc()));
        }
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
//        pipeline.addLast("message-decoder-header",new LengthFieldBasedFrameDecoder(65536,0,2,0,2));
//        pipeline.addLast("message-decoder",new StringDecoder());
//        pipeline.addLast("message-decoder",new MessagePackDecoder());
//        pipeline.addLast("message-encoder-header",new LengthFieldPrepender(2));
//        pipeline.addLast("message-encoder",new StringEncoder());
//        pipeline.addLast("message-encoder",new MessagePackEncoder());
        pipeline.addLast(new GoodsHandler());
    }
}
