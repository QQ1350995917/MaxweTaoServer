package maxwe.tao.server.goods.alimama;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * Created by Pengwei Ding on 2017-04-14 11:02.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class GoodsClient {
    private static volatile int counter = 0;
    private static final String HOST = System.getProperty("host", "127.0.0.1");
    private static final int PORT = Integer.parseInt(System.getProperty("port", "8899"));

    public void connect(String host, int port) throws Exception {
        final SslContext sslContext = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(sslContext.newHandler(ch.alloc(), host, port));
//                            pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                            pipeline.addLast("message-decoder-header",new LengthFieldBasedFrameDecoder(65536,0,2,0,2));
//                            pipeline.addLast("message-decoder",new StringDecoder());
                            pipeline.addLast("message-decoder",new MessagePackDecoder());
                            pipeline.addLast("message-encoder-header",new LengthFieldPrepender(2));
//                            pipeline.addLast("message-encoder",new StringEncoder());
                            pipeline.addLast("message-encoder",new MessagePackEncoder());
//                            pipeline.addLast(new MessagePackDecoder());
//                            pipeline.addLast(new MessagePackEncoder());
                            pipeline.addLast(new SimpleChannelInboundHandler<String>() {

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    String content = "{\n" +
                                            "    \"name\": \"BeJson\",\n" +
                                            "    \"url\": \"http://www.bejson.com\",\n" +
                                            "    \"page\": 88,\n" +
                                            "    \"isNonProfit\": true,\n" +
                                            "    \"address\": {\n" +
                                            "        \"street\": \"科技园路.\",\n" +
                                            "        \"city\": \"江苏苏州\",\n" +
                                            "        \"country\": \"中国\"\n" +
                                            "    },\n" +
                                            "    \"links\": [\n" +
                                            "        {\n" +
                                            "            \"name\": \"Google\",\n" +
                                            "            \"url\": \"http://www.google.com\"\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"name\": \"Baidu\",\n" +
                                            "            \"url\": \"http://www.baidu.com\"\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"name\": \"SoSo\",\n" +
                                            "            \"url\": \"http://www.SoSo.com\"\n" +
                                            "        }\n" +
                                            "    ]\n" +
                                            "}";
//                                    MessageBufferPacker messageBufferPacker = MessagePack.newDefaultBufferPacker();
//                                    MessagePacker messagePacker = messageBufferPacker.packString(content);

                                    ctx.channel().writeAndFlush(content);
                                }

                                @Override
                                public void channelRead0(ChannelHandlerContext ctx, String msg) {
                                    System.out.println("client get;" + msg);
                                    ctx.close();
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                                    cause.printStackTrace();
                                    ctx.close();
                                }
                            });
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            channel.closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        String host = HOST;
        int port = PORT;
        if (args != null && args.length > 0) {
            port = Integer.valueOf(args[0]);
        }
        for (int i = 0; i < 1; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        new GoodsClient().connect(host, PORT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();

            Thread.sleep(10);
        }

    }
}
