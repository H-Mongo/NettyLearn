package com.mongo.netty.example.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 *  介绍：
 *  netty 的入门案例，通过客户端和服务端两个程序之间的消息互动，探究 netty 的主从模型
 *  客户端发送：hello，服务端！
 *  服务端回复：hello，客户端！
 *  并通过自定义的 handler 完成服务端和客户端的业务逻辑
 *
 * @author hzuwei
 * @version 1.0
 * @date 2020/6/6 17:42
 */
public class NettyClient {

    public static void main(String[] args) throws Exception {
        // 客户端需要一个事件循环组
        final EventLoopGroup group = new NioEventLoopGroup();

        // 创建客户端启动对象
        // 注意客户端使用的不是 ServerBootStrap 而是 Bootstrap
        final Bootstrap bootstrap = new Bootstrap();
        try {
            // 设置相关参数
            bootstrap.group(group) // 设置线程组
                    .channel(NioSocketChannel.class) // 设置客户端通道的实现类（后续内部反射处理）
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler()); // 加入自己的处理器
                        }
                    });
            System.out.println("......客户端准备好了.....");

            // 启动客户端去连接服务端
            // channelFuture 涉及到 netty 的异步模型
            final ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            // 给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully(); // 关闭
        }



    }





}
