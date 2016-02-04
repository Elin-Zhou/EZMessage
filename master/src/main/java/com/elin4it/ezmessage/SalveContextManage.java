/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * @author ElinZhou
 * @version $Id: SalveContextManage.java , v 0.1 2016/2/3 15:07 ElinZhou Exp $
 */
public class SalveContextManage {
    private static final ConcurrentHashMap<String, SalveContext> aliveSalve = new ConcurrentHashMap<String, SalveContext>();
    private static final Logger                                  LOGGER     = Logger
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
     * 获取当前连接的salve的处理器
     * @return
     */
    public static List<SalveHandle> getAliveSalveHandles() {
        List<SalveHandle> salveHandles = new ArrayList<SalveHandle>();
        for (SalveContext context : aliveSalve.values()) {
            salveHandles.add(context.getSalveHandle());
        }
        return salveHandles;
    }

    /**
     * 获得salve的处理器
     * @param salveId
     * @return
     */
    public static SalveHandle getSalveHandle(String salveId) {
        return get(salveId).getSalveHandle();
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
     * @param writeThreadFuture
     */
    public static void setWriteThreadFuture(String salveId, Future<WriteThread> writeThreadFuture) {
        get(salveId).setWriteThreadFuture(writeThreadFuture);
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
     * 杀死所以与该从节点运行有关的线程
     * @param salveId
     */
    private static void kill(String salveId) {
        SalveContext salve = aliveSalve.get(salveId);
        if (salve != null) {
            while (!salve.getSalveHandleFuture().isDone())
                salve.getSalveHandleFuture().cancel(true);
            while (!salve.getReadThreadFuture().isDone())
                salve.getReadThreadFuture().cancel(true);
            while (!salve.getWriteThreadFuture().isDone())
                salve.getWriteThreadFuture().cancel(true);
        }

    }

    private static SalveContext get(String salveId) {
        return aliveSalve.get(salveId);
    }
}
