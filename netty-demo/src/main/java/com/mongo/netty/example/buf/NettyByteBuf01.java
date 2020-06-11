package com.mongo.netty.example.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author hzuwei
 * @version 1.0
 * @date 2020/6/11 17:33
 */
public class NettyByteBuf01 {

    public static void main(String[] args) {


        // 创建一个 ByteBuf，并初始化大小
        // 1. 创建一个对象，该对象含有一个数组，是一个byte[10]
        // 2. 在 netty 的 ByteBuf 中，不需要使用 flip() 进行反转，维护了 readerIndex 和 writerIndex
        // 3. 通过 readerIndex，writerIndex，capacity 将 ByteBuf 分为三个区域
        //      0---readerIndex：已经读取的区域
        //      readerIndex---writerIndex：可读的区域
        //      writerIndex---capacity：可写的区域
        final ByteBuf byteBuf = Unpooled.buffer(10);

        // 输入数据
        for (int i = 0; i < 10; ++i){
            byteBuf.writeByte(i);
        }
        System.out.println("byteBuf 的容量 capacity=" + byteBuf.capacity());
        // 输出数据，方式一
        for (int i = 0; i < byteBuf.capacity(); ++i){
            // getByte(i) 的使用不会导致 readerIndex 的变化
            System.out.println(byteBuf.getByte(i));
        }
        // 输出数据，方式二
        for (int i = 0; i < byteBuf.capacity(); ++i){
            // readerIndex() 的使用会导致 readerIndex 的变化，可读取的数据范围最大为 writerIndex ，而不是 capacity
            System.out.println(byteBuf.readerIndex());
        }





    }





}
