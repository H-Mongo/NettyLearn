package com.mongo.netty.example.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author hzuwei
 * @version 1.0
 * @date 2020/6/10 13:09
 */
public class TestServer {

    public static void main(String[] args) {

        final EventLoopGroup bossGroup = new NioEventLoopGroup();
        final EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            // 服务端启动配置，例如：线程组，通道，处理器等信息
            final ServerBootstrap serverBootstrap = new ServerBootstrap().group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());

            // 绑定端口并同步启动服务端
            final ChannelFuture channelFuture = serverBootstrap.bind(7777).sync();
            // 关闭 future
            channelFuture.channel().closeFuture().sync();

        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            // 关闭连接
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }



}
