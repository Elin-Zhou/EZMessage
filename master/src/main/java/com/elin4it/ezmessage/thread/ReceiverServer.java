/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.thread;

import com.elin4it.ezmessage.MessageResolve.MessageResolve;
import com.elin4it.ezmessage.SalveContext;
import com.elin4it.ezmessage.SalveContextManage;
import com.elin4it.ezmessage.SalveSocket;
import com.elin4it.ezmessage.message.Message;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 主节点接收Socket Server线程
 *
 * @author ElinZhou
 * @version $Id: ReceiverServer.java , v 0.1 2016/2/2 13:41 ElinZhou Exp $
 */
public class ReceiverServer implements Runnable {
    private static final Logger         LOGGER              = Logger
        .getLogger(ReceiverServer.class);
    private int                         port;
    private ServerSocket                serverSocket;
    private ExecutorService             clientExecutor;
    private MessageResolve              messageResolve;
    private LinkedBlockingQueue<String> receiveMessageQueue = new LinkedBlockingQueue<String>();
    private ExecutorService             resolveExecutor     = Executors.newSingleThreadExecutor();

    public ReceiverServer(int port, ExecutorService clientExecutor, MessageResolve messageResolve) {
        this.port = port;
        this.clientExecutor = clientExecutor;
        this.messageResolve = messageResolve;
    }

    public void sendMessage(Message message) {
        Collection<SalveContext> clients = SalveContextManage.getAliveSalveContext();
        for (SalveContext salver : clients) {
            salver.getSalveHandle().sendMessage(message);
        }
    }

    public void sendMessage(String salveId, Message message) {
        SalveContextManage.get(salveId).getSalveHandle().sendMessage(message);
    }

    public void sendMessage(List<String> clients, Message message) {
        for (String client : clients) {
            sendMessage(client, message);
        }
    }

    public void sendMessage(Map<String, Message> messageMap) {
        for (Map.Entry<String, Message> entry : messageMap.entrySet()) {
            SalveContextManage.get(entry.getKey()).getSalveHandle().sendMessage(entry.getValue());
        }
    }

    public void run() {
        try {
            LOGGER.info("服务启动");
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10);
            SalveSocket salveSocket;
            SalveHandle salveHandle;

            //如果配置了消息解析方式，则启动消息解析线程
            if (messageResolve != null) {
                MessageResolveThread messageResolveThread = new MessageResolveThread(messageResolve,
                    receiveMessageQueue);
                resolveExecutor.execute(messageResolveThread);
            }

            while (true) {
                try {
                    salveSocket = new SalveSocket(serverSocket.accept());
                } catch (IOException e) {
                    continue;
                }
                if (salveSocket != null) {
                    LOGGER.info("新的从节点接入，IP为：" + salveSocket.getSocket().getInetAddress());
                    salveHandle = new SalveHandle(salveSocket, receiveMessageQueue, messageResolve);

                    //把新的salve添加到上下文中
                    SalveContext salveContext = new SalveContext(salveSocket.getSalveId());
                    salveContext.setSalveHandle(salveHandle);
                    SalveContextManage.addSalve(salveContext);

                    Future salveHandleFuture = clientExecutor.submit(salveHandle);
                    SalveContextManage.setSalveHandleFuture(salveSocket.getSalveId(),
                        salveHandleFuture);
                    //把创建时间设为第一次得到心跳信号的时间
                    SalveContextManage.setLastHeartBeatTime(salveSocket.getSalveId(), new Date());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
