package com.mongo.netty.example.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author hzuwei
 * @version 1.0
 * @date 2020/6/6 16:52
 */
public class NettyServer {
    public static void main(String[] args) throws Exception {

        // 创建BossGroup 和 WorkerGroup
        // 说明：
        // 1. 创建了两个线程组，bossGroup和workerGroup
        // 2. bossGroup 只处理连接请求，真正的客户端业务处理，会交给 workerGroup 来完成
        // 3. 两个都是无限循环
        final EventLoopGroup bossGroup = new NioEventLoopGroup();
        final EventLoopGroup workerGroup = new NioEventLoopGroup();

        // 创建服务器端的启动对象，配置参数
        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        try{
            // 使用链式编程来设置
            serverBootstrap.group(bossGroup,workerGroup) // 设置两个线程组
                            .channel(NioServerSocketChannel.class) // 使用 NioServerSocketChannel 来作为服务器端通道实现
                            .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列等待连接的个数
                            .childOption(ChannelOption.SO_KEEPALIVE,true) // 设置保持活动连接状态
                            .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个通道初始化对象
                                // 给 pipeline 设置处理器
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ch.pipeline().addLast(new NettyServerHandler());
                                }
                            }) ;// 给我们的 workerGroup 的 EventLoop 对应的管道设置处理器

            System.out.println(".........服务器 准备好了........");

            // 绑定一个端口并且同步，生成了一个 channelFuture 对象
            // 启动服务器并绑定端口
            final ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();

            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 出现异常，关闭服务器
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }










}
