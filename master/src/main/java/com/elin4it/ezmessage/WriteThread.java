/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.elin4it.ezmessage.message.Message;
import org.apache.log4j.Logger;

/**
 * @author ElinZhou
 * @version $Id: ReadThread.java , v 0.1 2016/2/3 10:31 ElinZhou Exp $
 */
public class WriteThread implements Runnable {
    private static final Logger            LOGGER = Logger.getLogger(WriteThread.class);
    private SalveSocket                    salveSocket;
    private BufferedWriter                 out;
    private ConcurrentLinkedQueue<Message> sendMessageQueue;

    public WriteThread(SalveSocket salveSocket, ConcurrentLinkedQueue<Message> sendMessageQueue) {
        this.salveSocket = salveSocket;
        try {
            out = new BufferedWriter(
                new OutputStreamWriter(salveSocket.getSocket().getOutputStream()));
            this.sendMessageQueue = sendMessageQueue;
        } catch (Exception e) {

        }

    }

    public void run() {
        try {
            while (true) {
                Message message;
                if (!sendMessageQueue.isEmpty()) {
                    message = sendMessageQueue.poll();
                    message.setSendTime(new Date());
                    out.write(message.toString());
                    out.write("\n");
                    out.flush();
                    LOGGER.info("向" + salveSocket.getSalveId() + "发送数据：" + message.toString());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
