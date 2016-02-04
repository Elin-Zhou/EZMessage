package test;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class SalveLaunch {
    public static final String IP_ADDR = "localhost";//服务器地址 
    public static final int    PORT    = 12345;      //服务器端口号

    public static void main(String[] args) {
        System.out.println("客户端启动...");
        System.out.println("当接收到服务器端字符为 \"OK\" 的时候, 客户端将终止\n");
        Socket socket = null;
        try {
            //创建一个流套接字并将其连接到指定主机上的指定端口号
            socket = new Socket(IP_ADDR, PORT);
            //读取服务器端数据
            BufferedReader in = new BufferedReader(
                new InputStreamReader(new DataInputStream(socket.getInputStream())));
            //向服务器端发送数据
            BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new DataOutputStream(socket.getOutputStream())));
            String inString;
            while (true) {
                while ((inString = in.readLine()) != null && inString.length() != 0) {
                    System.out.println(inString);
                    out.write(new Date() + ":" + inString + "\n");
                    out.flush();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}