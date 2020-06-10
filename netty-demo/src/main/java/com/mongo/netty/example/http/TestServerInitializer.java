package com.mongo.netty.example.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author hzuwei
 * @version 1.0
 * @date 2020/6/10 13:14
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    // 初始化通道
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 向管道加入处理器

        // 得到管道
        final ChannelPipeline pipeline = ch.pipeline();

        // 加入一个 netty 提供的 httpServerCodec codec => [coder - decoder]
        // HttpServerCodec 说明：
        //      1. netty 所提供的处理 http 的编解码器
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        //      2. 增加一个自定义的处理器（handler）
        pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());

    }
}
