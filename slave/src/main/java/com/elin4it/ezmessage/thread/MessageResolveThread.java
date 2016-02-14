/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.thread;

import java.util.concurrent.LinkedBlockingQueue;

import com.elin4it.ezmessage.message.*;
import com.elin4it.ezmessage.messageResolve.MessageResolve;

/**
 * 消息解析线程
 * @author ElinZhou
 * @version $Id: MessageResolveThread.java , v 0.1 2016/2/4 11:40 ElinZhou Exp $
 */
public class MessageResolveThread implements Runnable {

    private MessageResolve              messageResolve;
    private LinkedBlockingQueue<String> receiveMessageQueue;

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
                        //处理系统消息
                    } else if (message instanceof CallBackMessage) {
                        //处理回调消息
                    } else if (message instanceof CustomMessage) {
                        //处理客户消息
                        messageResolve.resolve((CustomMessage) message);
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
