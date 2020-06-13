package com.mongo.netty.example.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author hzuwei
 * @version 1.0
 * @date 2020/6/13 16:54
 */
public class NettyByteBuf02 {

    public static void main(String[] args) {

        // 创建 ByteBuf
        final ByteBuf byteBuf = Unpooled.copiedBuffer("Hello world!", CharsetUtil.UTF_8);

        // 使用相关的 API
        if(byteBuf.hasArray()){
            // 获取数组
            final byte[] array = byteBuf.array();
            // 将 array 转成字符串
            System.out.println(new String(array,Charset.forName("utf-8")));
            // 具体类型为(UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf)，容量大于实际大小
            System.out.println("byteBuf=" + byteBuf);
            // 数组偏移量：0
            System.out.println(byteBuf.arrayOffset());
            // 可读取位置：0
            System.out.println(byteBuf.readerIndex());
            // 已写入位置：12
            System.out.println(byteBuf.writerIndex());
            // 容量：大于实际存储的内容大小
            System.out.println(byteBuf.capacity());
            // 可读字节数，使用readByte()，会改变该值
            final int len = byteBuf.readableBytes();
            System.out.println(len);

            // 使用 for 取出各个字符
            for (int i = 0; i < len; i++) {
                System.out.println((char) byteBuf.getByte(i));
            }
            // 按照某个范围读取：
            // 从索引 0 开始，取 4 个：hell
            System.out.println(byteBuf.getCharSequence(0,4,Charset.forName("utf-8")));
            // 从索引 4 开始，取 6 个：o worl
            System.out.println(byteBuf.getCharSequence(4,6,Charset.forName("utf-8")));


        }


    }



}
