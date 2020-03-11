package com.togo.mq.client.entity;

import lombok.*;

/**
 * @Author taiyn
 * @Description TODO
 * @Date 9:35 下午 2020/2/21
 **/
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Frame {

    public int type;

    public int channel;

    private byte[] payload;
}
