package com.togo.mq.client;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sun.tools.jconsole.Worker;

import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.Key;

/**
 * @Author taiyn
 * @Description TODO
 * @Date 9:03 下午 2020/2/15
 **/
public class TigerConnection {

    private String host = "127.0.0.1";
    private int port = 5672;

    private int major = 2;
    private int minor = 9;
    private int revision = 1;

    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    public void tcpConnect() {

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

        outputStream.write("AMQP".getBytes());
        outputStream.write(0);
        outputStream.write(major);
        outputStream.write(minor);
        outputStream.write(revision);
        outputStream.flush();
    }

    public void connectionStartOk() {}

    public static void main(String[] args)throws IOException {
//        TigerConnection tigerConnection = new TigerConnection();
//        tigerConnection.tcpConnect();
//        tigerConnection.sendHeader();


        Key key = new SecretKeySpec("secret".getBytes(), SignatureAlgorithm.HS256.getJcaName());
        String jws = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYWhhIn0.gbIjVFaWcwLQFptoxe0PVsFnI6JA05feQrCLoJjwp8U";
        String body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).toString();
        System.out.println(body);

    }
}
