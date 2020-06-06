package com.mongo.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


/**
 * 自定义一个 handler 需要继承 netty 规定好的 handlerAdapter
 * 这是我们自定的 handler ，才能称为一个 handler
 * @author hzuwei
 * @version 1.0
 * @date 2020/6/6 17:19
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    // 读取数据（这里我们可以读取客户端发送的消息）
    /*
       1. ChannelHandlerContext： 上下文对象，含有管道 pipeline ，通道 channel，地址
       2. Object： 客户发送的数据，默认是 Object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("服务端上下文对象：ctx =" + ctx);
        // 将 msg 转成一个 byteBuf
        // ByteBuf 是 netty 提供的，不是NIO 的 ByteBuffer，性能更高
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());

    }

    // 读取数据完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        // writeAndFlush 是
        // 将数据写入缓存，并刷新
        // 一般讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello，客户端！~o( =∩ω∩= )m喵喵喵~",CharsetUtil.UTF_8));

    }

    // 处理异常，一般需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
