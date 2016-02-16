/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.salve;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.elin4it.ezmessage.common.message.Message;
import com.elin4it.ezmessage.common.messageResolve.MessageResolve;
import com.elin4it.ezmessage.salve.thread.NodeServer;

/**
 * 从节点入口
 *
 * @author ElinZhou
 * @version $Id: SimpleNode.java , v 0.1 2016/2/14 15:56 ElinZhou Exp $
 */
public class SimpleNode {
    /**
     * master地址
     */
    private String          address;
    /**
     * master端口
     */
    private Integer         port;
    /**
     * 消息解析器（需注入）
     */
    private MessageResolve  messageResolve;
    private ExecutorService socketExecutor = Executors.newSingleThreadExecutor();

    /**
     * 构造方法
     * @param address master IP
     * @param port master port
     */
    public SimpleNode(String address, Integer port) {
        this.address = address;
        this.port = port;
    }

    /**
     * 启动salve
     */
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
