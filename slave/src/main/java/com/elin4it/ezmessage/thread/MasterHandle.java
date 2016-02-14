/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.elin4it.ezmessage.entity.EZSocket;
import com.elin4it.ezmessage.message.Message;
import com.elin4it.ezmessage.messageResolve.MessageResolve;
import com.elin4it.ezmessage.util.connect.ReadThread;
import com.elin4it.ezmessage.util.connect.WriteThread;

/**
 * 与master连接上后的处理线程
 * 
 * @author ElinZhou
 * @version $Id: MasterHandle.java , v 0.1 2016/2/14 16:27 ElinZhou Exp $
 */
public class MasterHandle implements Runnable {

    private com.elin4it.ezmessage.entity.EZSocket EZSocket;
    private static final Logger                   LOGGER              = Logger
        .getLogger(MasterHandle.class);
    private LinkedBlockingQueue<Message>          sendMessageQueue    = new LinkedBlockingQueue<Message>();
    private LinkedBlockingQueue<String>           receiveMessageQueue = new LinkedBlockingQueue<String>();
    private ExecutorService                       readWriteExecutor   = Executors
        .newFixedThreadPool(5);
    private MessageResolve                        messageResolve;
    private ExecutorService                       resolveExecutor     = Executors
        .newSingleThreadExecutor();

    public MasterHandle(EZSocket EZSocket, MessageResolve messageResolve) {
        this.EZSocket = EZSocket;
        this.messageResolve = messageResolve;
    }

    public void sendMessage(Message message) {
        sendMessageQueue.add(message);
    }

    @Override
    public void run() {
        try {

            while (NodeServer.masterContext == null) {
                Thread.sleep(100);
            }

            //如果配置了消息解析方式，则启动消息解析线程
            if (messageResolve != null) {
                MessageResolveThread messageResolveThread = new MessageResolveThread(messageResolve,
                    receiveMessageQueue);
                resolveExecutor.execute(messageResolveThread);
            }
            //启动心跳线程
            HeartBeatSender heartBeatSender = new HeartBeatSender();
            readWriteExecutor.submit(new Thread(heartBeatSender));
            NodeServer.masterContext.setHeartBeatSender(heartBeatSender);

            //启动读线程
            ReadThread readThread = new ReadThread(EZSocket, receiveMessageQueue);
            readWriteExecutor.submit(new Thread(readThread));
            NodeServer.masterContext.setReaderThread(readThread);

            //启动写线程
            WriteThread writeThread = new WriteThread(EZSocket, sendMessageQueue);
            readWriteExecutor.submit(writeThread);
            NodeServer.masterContext.setWriteThread(writeThread);

        } catch (Exception e) {
            LOGGER.error("控制器异常{}", e);
        }
    }
}
