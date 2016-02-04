/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.thread;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.elin4it.ezmessage.SalveSocket;
import com.elin4it.ezmessage.message.Message;
import org.apache.log4j.Logger;

/**
 * @author ElinZhou
 * @version $Id: ReadThread.java , v 0.1 2016/2/3 10:31 ElinZhou Exp $
 */
public class WriteThread implements Runnable {
    private static final Logger          LOGGER = Logger.getLogger(WriteThread.class);
    private SalveSocket                  salveSocket;
    private BufferedWriter               out;
    private LinkedBlockingQueue<Message> sendMessageQueue;
    private AtomicBoolean                isRun  = new AtomicBoolean(true);

    public WriteThread(SalveSocket salveSocket, LinkedBlockingQueue<Message> sendMessageQueue) {
        this.salveSocket = salveSocket;
        try {
            out = new BufferedWriter(
                new OutputStreamWriter(salveSocket.getSocket().getOutputStream()));
            this.sendMessageQueue = sendMessageQueue;
        } catch (Exception e) {

        }

    }

    public void setIsRun(boolean isRun) {
        this.isRun.set(isRun);
    }

    public void run() {
        try {
            while (isRun.get()) {
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
        } finally {
            LOGGER.info(salveSocket.getSalveId() + " 写线程被关闭");
        }

    }
}
