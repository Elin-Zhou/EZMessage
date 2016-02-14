/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.thread;

import java.util.concurrent.LinkedBlockingQueue;

import com.elin4it.ezmessage.message.*;
import com.elin4it.ezmessage.messageResolve.HeartBeatMessageResolve;
import com.elin4it.ezmessage.messageResolve.MessageResolve;

/**
 * 消息解析线程
 * @author ElinZhou
 * @version $Id: MessageResolveThread.java , v 0.1 2016/2/4 11:40 ElinZhou Exp $
 */
public class MessageResolveThread implements Runnable {

    private MessageResolve              messageResolve;
    private LinkedBlockingQueue<String> receiveMessageQueue;
    private MessageResolve              heartBeatMessageResolve = new HeartBeatMessageResolve();

    public MessageResolveThread(MessageResolve messageResolve,
                                LinkedBlockingQueue<String> receiveMessageQueue) {
        this.messageResolve = messageResolve;
        this.receiveMessageQueue = receiveMessageQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!receiveMessageQueue.isEmpty()) {
                    String messageString = receiveMessageQueue.take();
                    Message message = Message.convertMessage(messageString);
                    if (message instanceof SystemMessage) {
                        SystemMessage systemMessage = (SystemMessage) message;
                        //如果该系统消息为心跳信息，则通知心跳处理机制
                        if (systemMessage.getSystemMessageType() == SystemMessageType.HEART_BEAT) {
                            heartBeatMessageResolve.resolve(systemMessage);
                        }
                    } else if (message instanceof CallBackMessage) {

                    } else if (message instanceof CustomMessage) {

                        messageResolve.resolve(message);
                    } else {
                        continue;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
