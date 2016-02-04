/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author ElinZhou
 * @version $Id: ReadThread.java , v 0.1 2016/2/3 10:31 ElinZhou Exp $
 */
public class ReadThread implements Runnable {
    private static final Logger           LOGGER = Logger.getLogger(ReadThread.class);
    private SalveSocket salveSocket;
    private BufferedReader                in;
    private ConcurrentLinkedQueue<String> receiveMessageQueue;

    public ReadThread(SalveSocket salveSocket,
                      ConcurrentLinkedQueue<String> receiveMessageQueue) {
        this.salveSocket = salveSocket;
        try {
            in = new BufferedReader(
                new InputStreamReader(salveSocket.getSocket().getInputStream()));
            this.receiveMessageQueue = receiveMessageQueue;
        } catch (Exception e) {

        }

    }

    public void run() {
        try {
            String inString;
            while(true){
                while ((inString = in.readLine()) != null && inString.length() != 0) {
                    receiveMessageQueue.add(inString);
                    LOGGER.info("收到" + salveSocket.getSalveId() + "发送的数据：" + inString);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
