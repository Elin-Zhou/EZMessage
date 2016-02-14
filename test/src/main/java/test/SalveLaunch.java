package test;

import com.elin4it.ezmessage.SimpleNode;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class SalveLaunch {
    public static final String IP_ADDR = "localhost";//服务器地址
    public static final int    PORT    = 12345;      //服务器端口号

    public static void main(String[] args) {
        SimpleNode simpleNode = new SimpleNode(IP_ADDR, PORT);
        simpleNode.start();
    }
}