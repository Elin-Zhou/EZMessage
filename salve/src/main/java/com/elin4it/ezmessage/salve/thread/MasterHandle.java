/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.salve.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.elin4it.ezmessage.common.message.CallBackMessage;
import com.elin4it.ezmessage.salve.CallBackMessageManage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.elin4it.ezmessage.common.entity.EZSocket;
import com.elin4it.ezmessage.common.message.Message;
import com.elin4it.ezmessage.common.messageResolve.MessageResolve;
import com.elin4it.ezmessage.common.util.connect.ReadThread;
import com.elin4it.ezmessage.common.util.connect.WriteThread;

/**
 * 与master连接上后的处理线程
 * 
 * @author ElinZhou
 * @version $Id: MasterHandle.java , v 0.1 2016/2/14 16:27 ElinZhou Exp $
 */
public class MasterHandle implements Runnable {

    private static final Logger LOGGER      = Logger.getLogger(MasterHandle.class);
    private static int          RETRY_TIMES = 10;

    private EZSocket EZSocket;

    private LinkedBlockingQueue<Message> sendMessageQueue    = new LinkedBlockingQueue<Message>();
    private LinkedBlockingQueue<String>  receiveMessageQueue = new LinkedBlockingQueue<String>();
    private ExecutorService              readWriteExecutor   = Executors.newFixedThreadPool(5);
    private MessageResolve               messageResolve;
    private ExecutorService              resolveExecutor     = Executors.newSingleThreadExecutor();

    public MasterHandle(EZSocket EZSocket, MessageResolve messageResolve) {
        this.EZSocket = EZSocket;
        this.messageResolve = messageResolve;
    }

    public boolean sendMessage(Message message) {

        if (StringUtils.isBlank(message.getSender())){
            return false;
        }

        //如果该消息为回调消息，则通知回调消息管理
        if (message instanceof CallBackMessage) {
            CallBackMessageManage.notify((CallBackMessage) message);
        }
        sendMessageQueue.add(message);
        return true;
    }

    @Override
    public void run() {
        try {

            //启动消息解析线程
            MessageResolveThread messageResolveThread = new MessageResolveThread(messageResolve,
                receiveMessageQueue);
            resolveExecutor.submit(messageResolveThread);

            //启动读线程
            ReadThread readThread = new ReadThread(EZSocket, receiveMessageQueue);
            readWriteExecutor.submit(new Thread(readThread));
            NodeServer.masterContext.setReaderThread(readThread);

            //判断连接是否已初始化完毕
            while (NodeServer.masterContext == null
                   || StringUtils.isBlank(NodeServer.masterContext.getSelfId())) {
                Thread.sleep(200);
                RETRY_TIMES--;
                if (RETRY_TIMES == 0) {
                    throw new RuntimeException("等待初始化超时");
                }
            }

            //启动心跳线程
            HeartBeatSender heartBeatSender = new HeartBeatSender();
            readWriteExecutor.submit(new Thread(heartBeatSender));
            NodeServer.masterContext.setHeartBeatSender(heartBeatSender);

            //启动写线程
            WriteThread writeThread = new WriteThread(EZSocket, sendMessageQueue);
            readWriteExecutor.submit(writeThread);
            NodeServer.masterContext.setWriteThread(writeThread);

        } catch (Exception e) {
            LOGGER.error("控制器异常{}", e);
        }
    }
}
