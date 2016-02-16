/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.salve.thread;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.elin4it.ezmessage.common.entity.EZSocket;
import com.elin4it.ezmessage.common.messageResolve.MessageResolve;
import com.elin4it.ezmessage.salve.MasterContext;

/**
 * 从节点连接线程
 *
 * @author ElinZhou
 * @version $Id: NodeServer.java , v 0.1 2016/2/14 16:10 ElinZhou Exp $
 */

public class NodeServer implements Runnable {

    private static final Logger LOGGER               = Logger.getLogger(NodeServer.class);
    private ExecutorService     masterHandleExecutor = Executors.newSingleThreadExecutor();

    public static MasterContext masterContext;

    /**
     * 连接重试次数
     */
    private int retryTime = 5;

    private String address;

    private Integer port;

    private MessageResolve messageResolve;

    private static EZSocket ezSocket;

    public NodeServer(String address, Integer port, MessageResolve messageResolve) {
        this.address = address;
        this.port = port;
        this.messageResolve = messageResolve;
    }

    public int getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(int retryTime) {
        this.retryTime = retryTime;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < retryTime; i++) {
                try {
                    ezSocket = new EZSocket(address, port);

                    //创建运行上下文
                    NodeServer.masterContext = new MasterContext();

                    MasterHandle masterHandle = new MasterHandle(ezSocket, messageResolve);
                    masterHandleExecutor.submit(masterHandle);
                    NodeServer.masterContext.setMasterHandle(masterHandle);

                    LOGGER.info("连接到服务器");
                    break;
                } catch (IOException e) {
                    LOGGER.warn("连接失败，重试" + (i + 1) + "次");
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {

        }

    }

}
