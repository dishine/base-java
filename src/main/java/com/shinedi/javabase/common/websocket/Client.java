package com.shinedi.javabase.common.websocket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: D-S
 * @Date: 2020/7/26 9:36 下午
 */
public class Client  extends ChannelInitializer<SocketChannel>   {
    //连接地址
    private String  url;

    private Bootstrap bootstrap;

    private EventLoopGroup worker;

    private ExecutorService executorService;

    private ChannelPromise handshakeFuture;

    private ClientHandler handler;

    private HttpHeaders  headers;
    private  WebSocketClientHandshaker webSocketClientHandshaker;

    public Client(String url,HttpHeaders headers) {
        this.url = url;
        this.headers = headers;
        this.bootstrap = new  Bootstrap();
        this.executorService = Executors.newFixedThreadPool(3);
        this.worker = new NioEventLoopGroup();
        this.bootstrap.group(this.worker);
        this.bootstrap.channel(NioSocketChannel.class);
    }

    public String  start (){
        executorService.submit(() -> {
            // 配置客户端的NIO线程组
            EventLoopGroup clientGroup = new NioEventLoopGroup();
            try {
                this.handler = new ClientHandler(WebSocketClientHandshakerFactory.newHandshaker(
                        new URI(this.url),
                        WebSocketVersion.V13,
                        null,
                        false,
                        this.headers)
                );
                // 配置bootstrap
                bootstrap.handler(
                        this
                );
                // 发起异步连接操作  同步方法待成功
                Channel channel = bootstrap.connect(new InetSocketAddress(this.url,0)).sync().channel();
                // 等待客户端链路关闭
                channel.closeFuture().sync();
            } catch (InterruptedException | URISyntaxException e) {
                e.printStackTrace();
            } finally {
                // 优雅退出，释放NIO线程组
                clientGroup.shutdownGracefully();
            }
        });

        return null;
    }

    /**
     * 初始化Channel
     *
     * @param socketChannel socketChannel
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws NoSuchAlgorithmException {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 将请求与应答消息编码或者解码为HTTP消息
        pipeline.addLast(new HttpClientCodec());
        if (this.url.startsWith("wss")){
            SSLEngine sslEngine = SSLContext.getDefault().createSSLEngine();
            pipeline.addLast("ssl", new SslHandler(sslEngine));
        }
        // 将http消息的多个部分组合成一条完整的HTTP消息
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));

        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        // 客户端Handler
        pipeline.addLast("handler", this.handler);

    }

}
