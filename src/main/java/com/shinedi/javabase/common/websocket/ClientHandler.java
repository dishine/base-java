package com.shinedi.javabase.common.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: D-S
 * @Date: 2020/7/26 9:52 下午
 */
public class ClientHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);


    private final WebSocketClientHandshaker webSocketClientHandshaker;

    private ChannelPromise handshakeFuture;


    public ClientHandler(WebSocketClientHandshaker webSocketClientHandshaker) {
        this.webSocketClientHandshaker = webSocketClientHandshaker;
    }
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    /**
     * 异常
     *
     * @param channelHandlerContext channelHandlerContext
     * @param cause                 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
        LOGGER.info("===[exception]: {}===", cause.getMessage());
        // 移出通道
        channelHandlerContext.close();
    }

    /**
     * 当客户端主动链接服务端的链接后，调用此方法
     *
     * @param channelHandlerContext ChannelHandlerContext
     */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        LOGGER.info("===[建立连接]===");
        Channel channel = channelHandlerContext.channel();

        webSocketClientHandshaker.handshake(channel);
    }

    /**
     * 与服务端断开连接时
     *
     * @param channelHandlerContext channelHandlerContext
     */
    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) {
        Channel channel = channelHandlerContext.channel();
        // 移出通道
        LOGGER.info("===[连接断开] : {}===",channel.remoteAddress());

    }

    /**
     * 读完之后调用的方法
     *
     * @param channelHandlerContext ChannelHandlerContext
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.flush();
    }

    /**
     * 接收消息
     *
     * @param channelHandlerContext channelHandlerContext
     * @param msg                   msg
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {

        Channel channel = channelHandlerContext.channel();
        LOGGER.info("===[message] : {}===",msg);
        channel.write("ping");
        if (!webSocketClientHandshaker.isHandshakeComplete()) {
            webSocketClientHandshaker.finishHandshake(channel, (FullHttpResponse) msg);
            handshakeFuture.setSuccess();
            LOGGER.info("===[message] : {}===",msg);
            return;
        }
        channelHandlerContext.flush();

        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            throw new IllegalStateException(
                    "Unexpected FullHttpResponse (getStatus=" + response.status() +
                            ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        }
    }
}
