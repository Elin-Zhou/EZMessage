/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import com.elin4it.ezmessage.message.Message;
import org.apache.log4j.Logger;

/**
 * 主节点接收Socket Server线程
 *
 * @author ElinZhou
 * @version $Id: ReceiverServer.java , v 0.1 2016/2/2 13:41 ElinZhou Exp $
 */
public class ReceiverServer implements Runnable {
    private static final Logger           LOGGER              = Logger
        .getLogger(ReceiverServer.class);
    private int                           port;
    private AtomicBoolean                 isRun               = new AtomicBoolean(false);
    private ServerSocket                  serverSocket;
    private ExecutorService               clientExecutor;
    private ConcurrentLinkedQueue<String> receiveMessageQueue = new ConcurrentLinkedQueue<String>();

    public ReceiverServer(int port, ExecutorService clientExecutor) {
        this.port = port;
        this.clientExecutor = clientExecutor;
        isRun.set(true);
    }

    public void start() {
        isRun.set(true);
        LOGGER.info("服务启动");
    }

    public void pause() {
        isRun.set(false);
        LOGGER.info("服务暂停");
    }

    public void goOn() {
        isRun.set(true);
        LOGGER.info("服务继续");
    }

    public void sendMessage(Message message) {
        Collection<SalveHandle> clients = SalveContextManage.getAliveSalveHandles();
        for (SalveHandle client : clients) {
            client.sendMessage(message);
        }
    }

    public void sendMessage(String salveId, Message message) {
        SalveContextManage.getSalveHandle(salveId).sendMessage(message);
    }

    public void sendMessage(List<String> clients, Message message) {
        for (String client : clients) {
            sendMessage(client, message);
        }
    }

    public void sendMessage(Map<String, Message> messageMap) {
        for (Map.Entry<String, Message> entry : messageMap.entrySet()) {
            SalveContextManage.getSalveHandle(entry.getKey()).sendMessage(entry.getValue());
        }
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10);
            SalveSocket salveSocket;
            SalveHandle salveHandle;
            while (true) {
                try {
                    salveSocket = new SalveSocket(serverSocket.accept());
                } catch (IOException e) {
                    continue;
                }
                if (salveSocket != null) {
                    if (isRun.get()) {
                        LOGGER.info("新的从节点接入，IP为：" + salveSocket.getSocket().getInetAddress());
                        salveHandle = new SalveHandle(salveSocket, receiveMessageQueue);

                        //把新的salve添加到上下文中
                        SalveContext salveContext = new SalveContext(salveSocket.getSalveId());
                        salveContext.setSalveHandle(salveHandle);
                        SalveContextManage.addSalve(salveContext);

                        Future salveHandleFuture = clientExecutor.submit(salveHandle);
                        SalveContextManage.setSalveHandleFuture(salveSocket.getSalveId(),
                            salveHandleFuture);
                    } else {
                        LOGGER.info("新的从节点接入，IP为：" + salveSocket.getSocket().getInetAddress()
                                    + "，但是服务端暂停，所以关闭该连接。");
                        salveSocket.getSocket().close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
