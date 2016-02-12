/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.thread;

import com.elin4it.ezmessage.MessageResolve.MessageResolve;
import com.elin4it.ezmessage.SalveContextManage;
import com.elin4it.ezmessage.SalveSocket;
import com.elin4it.ezmessage.message.Message;
import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 从节点处理线程
 * @author ElinZhou
 * @version $Id: SalveHandle.java , v 0.1 2016/2/2 13:48 ElinZhou Exp $
 */
public class SalveHandle implements Runnable {
    private SalveSocket                  salveSocket;
    private static final Logger          LOGGER            = Logger.getLogger(SalveHandle.class);
    private LinkedBlockingQueue<Message> sendMessageQueue  = new LinkedBlockingQueue<Message>();
    private LinkedBlockingQueue<String>  receiveMessageQueue;
    private ExecutorService              readWriteExecutor = Executors.newFixedThreadPool(5);
    private MessageResolve               messageResolve;
    /**
     * 检查salve情况时延：ms
     */
    private long                         checkDelay        = 100;

    public SalveHandle(SalveSocket salveSocket, LinkedBlockingQueue<String> receiveMessageQueue,
                       MessageResolve messageResolve) {
        this.salveSocket = salveSocket;
        this.receiveMessageQueue = receiveMessageQueue;
        this.messageResolve = messageResolve;
    }

    public void sendMessage(Message message) {
        sendMessageQueue.add(message);
    }

    public SalveSocket getSalveSocket() {
        return salveSocket;
    }

    public void run() {

        try {
            //启动读线程
            ReadThread readThread = new ReadThread(salveSocket, receiveMessageQueue);
            Future readThreadFuture = readWriteExecutor.submit(new Thread(readThread));
            //设置运行上下文
            SalveContextManage.setReadThreadFuture(salveSocket.getSalveId(), readThreadFuture);

            //启动写线程
            WriteThread writeThread = new WriteThread(salveSocket, sendMessageQueue);
            Future writeThreadFuture = readWriteExecutor.submit(writeThread);
            //设置运行上下文
            SalveContextManage.setWriteThread(salveSocket.getSalveId(), writeThread);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
