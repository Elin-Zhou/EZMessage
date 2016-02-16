/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.common.util.connect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.elin4it.ezmessage.common.entity.EZSocket;

/**
 * @author ElinZhou
 * @version $Id: ReadThread.java , v 0.1 2016/2/3 10:31 ElinZhou Exp $
 */
public class ReadThread implements Runnable {
    private static final Logger         LOGGER = Logger.getLogger(ReadThread.class);
    private EZSocket EZSocket;
    private BufferedReader              in;
    private LinkedBlockingQueue<String> receiveMessageQueue;

    public ReadThread(EZSocket EZSocket, LinkedBlockingQueue<String> receiveMessageQueue) {
        this.EZSocket = EZSocket;
        try {
            in = new BufferedReader(
                new InputStreamReader(EZSocket.getSocket().getInputStream()));
            this.receiveMessageQueue = receiveMessageQueue;
        } catch (Exception e) {

        }

    }

    public BufferedReader getIn() {
        return in;
    }

    public void run() {
        try {
            String inString;
            while (true) {
                while ((inString = in.readLine()) != null && inString.length() != 0) {
                    receiveMessageQueue.put(inString);
//                    LOGGER.info("收到" + EZSocket.getId() + "发送的数据：" + inString);
                }
                Thread.sleep(100);
            }

        }catch (SocketException socketException){
            //salve由于某种原因被关闭，所以关闭读线程
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            LOGGER.info(EZSocket.getId() + " 读线程被关闭");
        }

    }
}
