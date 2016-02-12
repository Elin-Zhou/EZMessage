/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage;

import com.elin4it.ezmessage.MessageResolve.MessageResolve;
import com.elin4it.ezmessage.message.Message;
import com.elin4it.ezmessage.thread.CheckSocketStatus;
import com.elin4it.ezmessage.thread.ReceiverServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 主节点接收者
 *
 * @author ElinZhou
 * @version $Id: SimperReceiver.java , v 0.1 2016/2/2 13:23 ElinZhou Exp $
 */
public class SimperReceiver {

    private int             port;
    private ReceiverServer  receiverServer;
    private ExecutorService socketServiceExecutor = Executors.newFixedThreadPool(2);
    private ExecutorService clientExecutor        = Executors.newCachedThreadPool();
    private MessageResolve  messageResolve;

    public SimperReceiver(int port) {
        this.port = port;

    }

    public boolean start() {
        receiverServer = new ReceiverServer(port, clientExecutor, messageResolve);
        socketServiceExecutor.submit(receiverServer);
        Future future =socketServiceExecutor.submit(new CheckSocketStatus());
        return true;
    }

    public boolean stop() {
        return false;
    }

    public void sendMessage(Message message) {
        receiverServer.sendMessage(message);
    }

    public MessageResolve getMessageResolve() {
        return messageResolve;
    }

    public void setMessageResolve(MessageResolve messageResolve) {
        this.messageResolve = messageResolve;
    }
}
