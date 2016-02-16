/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.master.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import com.elin4it.ezmessage.master.SalveContextManage;
import org.apache.log4j.Logger;

import com.elin4it.ezmessage.common.entity.EZSocket;
import com.elin4it.ezmessage.common.message.Message;
import com.elin4it.ezmessage.common.messageResolve.MessageResolve;
import com.elin4it.ezmessage.common.util.connect.ReadThread;
import com.elin4it.ezmessage.common.util.connect.WriteThread;

/**
 * 从节点处理线程
 * @author ElinZhou
 * @version $Id: SalveHandle.java , v 0.1 2016/2/2 13:48 ElinZhou Exp $
 */
public class SalveHandle implements Runnable {
    private EZSocket EZSocket;
    private static final Logger          LOGGER            = Logger.getLogger(SalveHandle.class);
    private LinkedBlockingQueue<Message> sendMessageQueue  = new LinkedBlockingQueue<Message>();
    private LinkedBlockingQueue<String>  receiveMessageQueue;
    private ExecutorService              readWriteExecutor = Executors.newFixedThreadPool(5);
    private MessageResolve               messageResolve;
    /**
     * 检查salve情况时延：ms
     */
    private long                         checkDelay        = 100;

    public SalveHandle(EZSocket EZSocket, LinkedBlockingQueue<String> receiveMessageQueue,
                       MessageResolve messageResolve) {
        this.EZSocket = EZSocket;
        this.receiveMessageQueue = receiveMessageQueue;
        this.messageResolve = messageResolve;
    }

    public void sendMessage(Message message) {
        sendMessageQueue.add(message);
    }

    public EZSocket getEZSocket() {
        return EZSocket;
    }

    public void run() {

        try {
            //启动读线程
            ReadThread readThread = new ReadThread(EZSocket, receiveMessageQueue);
            Future readThreadFuture = readWriteExecutor.submit(new Thread(readThread));
            //设置运行上下文
            SalveContextManage.setReadThreadFuture(EZSocket.getId(), readThreadFuture);

            //启动写线程
            WriteThread writeThread = new WriteThread(EZSocket, sendMessageQueue);
            Future writeThreadFuture = readWriteExecutor.submit(writeThread);
            //设置运行上下文
            SalveContextManage.setWriteThread(EZSocket.getId(), writeThread);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
