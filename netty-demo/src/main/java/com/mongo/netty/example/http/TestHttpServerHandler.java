package com.mongo.netty.example.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 *  说明：
 *     1. SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter 的子类
 *     2. HttpObject 客户端和服务器端相互通讯的数据被封装成 HttpObject
 *
 * @author hzuwei
 * @version 1.0
 * @date 2020/6/10 13:10
 *
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    // 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        // 判断 msg 是不是 HttpRequest请求
        if(msg instanceof HttpRequest){
            System.out.println(" msg 类型：" + msg.getClass());
            System.out.println("客户端地址：" + ctx.channel().remoteAddress());

            // 获取请求对象
            HttpRequest httpRequest = (HttpRequest) msg;
            // 获取 URI，通过 URI 过滤特定资源
            final URI uri = new URI(httpRequest.uri());
            // 请求网站图标
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求了 favicon.ico，不响应！");
                return;
            }

            // 回复信息给浏览器（http协议）
            ByteBuf content = Unpooled.copiedBuffer("Hello, 我是服务器！", CharsetUtil.UTF_8);
            // 构造一个http的响应，即 httpResponse
            final FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf8")
                                .set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            // 将构建好的 Response 返回
            ctx.writeAndFlush(httpResponse);

        }




    }
}
