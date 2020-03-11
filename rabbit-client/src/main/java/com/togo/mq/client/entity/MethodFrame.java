package com.togo.mq.client.entity;

import lombok.Data;

/**
 * @Author taiyn
 * @Description TODO
 * @Date 10:13 下午 2020/2/21
 **/
@Data
public class MethodFrame extends Frame {

    private int classId;
    private int methodId;
    private byte[] arguments;
}
