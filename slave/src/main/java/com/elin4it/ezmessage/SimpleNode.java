/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage;

import com.elin4it.ezmessage.message.Message;
import com.elin4it.ezmessage.messageResolve.MessageResolve;
import com.elin4it.ezmessage.thread.NodeServer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 从节点入口
 *
 * @author ElinZhou
 * @version $Id: SimpleNode.java , v 0.1 2016/2/14 15:56 ElinZhou Exp $
 */
public class SimpleNode {
    private String          address;
    private Integer         port;
    private MessageResolve  messageResolve;
    private ExecutorService socketExecutor = Executors.newSingleThreadExecutor();

    public SimpleNode(String address, Integer port) {
        this.address = address;
        this.port = port;
    }

    public void start() {
        NodeServer nodeServer = new NodeServer(address, port, messageResolve);
        socketExecutor.submit(nodeServer);
    }

    public void sendMessage(Message message) {
        NodeServer.masterContext.getMasterHandle().sendMessage(message);
    }

    public MessageResolve getMessageResolve() {
        return messageResolve;
    }

    public void setMessageResolve(MessageResolve messageResolve) {
        this.messageResolve = messageResolve;
    }
}
