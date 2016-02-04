/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage;

import com.elin4it.ezmessage.message.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private ExecutorService clientExecutor = Executors.newCachedThreadPool();

    public SimperReceiver(int port) {
        this.port = port;
        receiverServer = new ReceiverServer(port,clientExecutor);
    }

    public boolean start() {
        receiverServer.start();
        socketServiceExecutor.execute(receiverServer);
//        socketServiceExecutor.execute(new CheckSocketStatus());
        return true;
    }

    public boolean pause() {
        receiverServer.pause();
        return true;
    }

    public boolean goOn() {
        receiverServer.goOn();
        return true;
    }

    public boolean stop() {
        return false;
    }

    public void sendMessage(Message message) {
        receiverServer.sendMessage(message);
    }

}
