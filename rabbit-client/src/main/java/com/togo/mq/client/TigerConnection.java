package com.togo.mq.client;


import com.togo.mq.client.entity.Frame;
import com.togo.mq.client.entity.MethodFrame;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @Author taiyn
 * @Description TODO
 * @Date 9:03 下午 2020/2/15
 **/
public class TigerConnection {

    private String host = "127.0.0.1";
    private int port = 5672;

    private int major = 0;
    private int minor = 9;
    private int revision = 1;

    private boolean open = false;

    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    public void tcpConnect() {

        System.out.println("TCP connect");
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(host, port));
            inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendHeader() throws IOException {

        System.out.println("Sending header~");
        outputStream.write("AMQP".getBytes());
        outputStream.write(0);
        outputStream.write(major);
        outputStream.write(minor);
        outputStream.write(revision);
        outputStream.flush();
    }

    public void startReceiveThread() {

        System.out.println("Start receive thread");
        open = true;
        Thread thread = new Thread(() -> {

            while (open) {

                try {
                    Frame frame = read(inputStream);
//                    System.out.println(frame);
                } catch (Exception e) {
                    e.printStackTrace();
                    open = false;
                }
            }
        });

        thread.start();
    }

    private Frame read(DataInputStream inputStream) throws IOException {

        int type = inputStream.readUnsignedByte();
        int channel = inputStream.readUnsignedShort();
        int payloadSize = inputStream.readInt();
        byte[] payload = new byte[payloadSize];
        inputStream.readFully(payload);

        return new Frame(type, channel, payload);
    }

    public void connectionStartOk() throws IOException {

//        MethodFrame methodFrame = MethodFrame.builder()
//                .classId(10)
//                .methodId(11)
//                .build();

        System.out.println("Sending startOk~");
        outputStream.write(1);
        outputStream.write(0);
        outputStream.write(40);
        outputStream.write(10);
        outputStream.write(11);
        outputStream.write(206);
        outputStream.flush();
    }

    public static void main(String[] args) throws IOException {
        TigerConnection tigerConnection = new TigerConnection();
        tigerConnection.tcpConnect();

        tigerConnection.startReceiveThread();
        tigerConnection.sendHeader();
        tigerConnection.connectionStartOk();

        tigerConnection.sendHeader();
    }
}
