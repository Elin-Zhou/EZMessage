/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.elin4it.ezmessage.util.connect.ReadThread;
import com.elin4it.ezmessage.thread.SalveHandle;
import com.elin4it.ezmessage.util.connect.WriteThread;

/**
 * @author ElinZhou
 * @version $Id: SalveContextManage.java , v 0.1 2016/2/3 15:07 ElinZhou Exp $
 */
public class SalveContextManage {
    /**
     * 心跳最大时间间隔（ms）
     */
    public static final long                                     HEART_BEAT_DELAY = 5000;
    private static final ConcurrentHashMap<String, SalveContext> aliveSalve       = new ConcurrentHashMap<String, SalveContext>();
    private static final Logger                                  LOGGER           = Logger
        .getLogger(SalveContextManage.class);

    /**
     * 不允许实例化
     */
    private SalveContextManage() {
    }

    /**
     * 添加从节点运行时上下文
     * @param salveContext 上下文信息
     */
    public static void addSalve(SalveContext salveContext) {
        aliveSalve.put(salveContext.getSalveId(), salveContext);
    }

    /**
     * 移除一个从节点，将会杀死所以与该从节点运行有关的线程
     * @param salveId
     */
    public static void removeSalve(String salveId) {
        kill(salveId);
        aliveSalve.remove(salveId);
        LOGGER.info(salveId + "节点断开连接");
    }

    /**
     * 获取当前连接的salve列表
     * @return
     */
    public static List<String> getAliveSalves() {
        List<String> clients = new ArrayList<String>(aliveSalve.keySet());
        return clients;
    }

    /**
     * 获取当前连接的salve的上下文
     * @return
     */
    public static Collection<SalveContext> getAliveSalveContext() {
        return aliveSalve.values();
    }

    /**
     * 设置从节点处理线程
     * @param salveId
     * @param salveHandle
     */
    public static void setSalveHandle(String salveId, SalveHandle salveHandle) {
        get(salveId).setSalveHandle(salveHandle);
    }

    /**
     * 设置从节点处理线程Future
     * @param salveId
     * @param salveHandleFuture
     */
    public static void setSalveHandleFuture(String salveId, Future<SalveHandle> salveHandleFuture) {
        get(salveId).setSalveHandleFuture(salveHandleFuture);
    }

    /**
     * 设置从节点写线程Future
     * @param salveId
     * @param writeThread
     */
    public static void setWriteThread(String salveId, WriteThread writeThread) {
        get(salveId).setWriteThread(writeThread);
    }

    /**
     * 设置从节点读线程Future
     * @param salveId
     * @param readThreadFuture
     */
    public static void setReadThreadFuture(String salveId, Future<ReadThread> readThreadFuture) {
        get(salveId).setReadThreadFuture(readThreadFuture);
    }

    /**
     * 设置接收到心跳的时间
     * @param salveId
     * @param lastHeartBeatTime
     */
    public static void setLastHeartBeatTime(String salveId, Date lastHeartBeatTime) {
        get(salveId).setLastHeartBeatTime(lastHeartBeatTime);
    }

    /**
     * 获取salve的上下文
     * @param salveId
     * @return
     */
    public static SalveContext get(String salveId) {
        return aliveSalve.get(salveId);
    }

    /**
     * 杀死所以与该从节点运行有关的线程
     * @param salveId
     */
    private static void kill(String salveId) {
        SalveContext salve = aliveSalve.get(salveId);
        if (salve != null) {
            try {
                salve.getSalveHandle().getEZSocket().getSocket().close();
                salve.getWriteThread().setIsRun(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
