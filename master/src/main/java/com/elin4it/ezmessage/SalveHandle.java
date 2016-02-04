/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage;

import com.elin4it.ezmessage.message.Message;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 从节点处理线程
 * @author ElinZhou
 * @version $Id: SalveHandle.java , v 0.1 2016/2/2 13:48 ElinZhou Exp $
 */
public class SalveHandle implements Runnable {
    private SalveSocket                    salveSocket;
    private static final Logger            LOGGER              = Logger
        .getLogger(SalveHandle.class);
    private ConcurrentLinkedQueue<Message> sendMessageQueue    = new ConcurrentLinkedQueue<Message>();
    private ConcurrentLinkedQueue<String>  receiveMessageQueue;
    private ExecutorService                readWriteExecutor   = Executors.newFixedThreadPool(2);
    /**
     * 检查salve情况时延：ms
     */
    private long                           checkDelay          = 100;

    public SalveHandle(SalveSocket salveSocket, ConcurrentLinkedQueue<String>  receiveMessageQueue) {
        this.salveSocket = salveSocket;
        this.receiveMessageQueue = receiveMessageQueue;
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
            Future readThreadFuture = readWriteExecutor.submit(readThread);
            //设置运行上下文
            SalveContextManage.setReadThreadFuture(salveSocket.getSalveId(), readThreadFuture);

            //启动写线程
            WriteThread writeThread = new WriteThread(salveSocket, sendMessageQueue);
            Future writeThreadFuture = readWriteExecutor.submit(writeThread);
            //设置运行上下文
            SalveContextManage.setWriteThreadFuture(salveSocket.getSalveId(), writeThreadFuture);

            //反复检查salve可用性
//            while (true) {
                if (!salveSocket.isConnected()) {
//                    SalveContextManage.removeSalve(salveSocket.getSalveId());
////                    break;
//                }
//                try {
//                    Thread.sleep(checkDelay);
//                } catch (InterruptedException e) {
//                    break;
//                }
//
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
