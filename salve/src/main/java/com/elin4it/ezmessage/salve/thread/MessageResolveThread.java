/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.salve.thread;

import java.util.concurrent.LinkedBlockingQueue;

import com.elin4it.ezmessage.common.message.*;
import com.elin4it.ezmessage.common.messageResolve.MessageResolve;
import com.elin4it.ezmessage.salve.messageResolve.CallBackMessageResolve;
import com.elin4it.ezmessage.salve.messageResolve.InitMessageResolve;

/**
 * 消息解析线程
 * @author ElinZhou
 * @version $Id: MessageResolveThread.java , v 0.1 2016/2/4 11:40 ElinZhou Exp $
 */
public class MessageResolveThread implements Runnable {

    private MessageResolve              messageResolve;
    private LinkedBlockingQueue<String> receiveMessageQueue;
    private MessageResolve              initMessageResolve     = new InitMessageResolve();
    private MessageResolve              callBackMessageResolve = new CallBackMessageResolve();

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
                        SystemMessage systemMessage = (SystemMessage) message;
                        if (systemMessage
                            .getSystemMessageType() == SystemMessageType.INIT_MESSAGE) {
                            //处理初始化消息
                            initMessageResolve.resolve(systemMessage);
                        }
                    } else if (message instanceof CallBackMessage) {
                        //处理回调消息
                        callBackMessageResolve.resolve(message);
                    } else if (message instanceof CustomMessage && messageResolve != null) {
                        //处理客户消息
                        messageResolve.resolve(message);
                    } else {
                        continue;
                    }
                }
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
