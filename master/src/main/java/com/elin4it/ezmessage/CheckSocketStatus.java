/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 *
 * 检查salve的健康情况并处理
 * @author ElinZhou
 * @version $Id: CheckSocketStatus.java , v 0.1 2016/2/3 14:45 ElinZhou Exp $
 */
public class CheckSocketStatus implements Runnable {

    /**
     * 当前的所有socket
     */
    private ConcurrentHashMap<String, SalveHandle> balanceSocketMap ;
    private ExecutorService clientExecutor;

    public CheckSocketStatus (ConcurrentHashMap<String, SalveHandle> balanceSocketMap, ExecutorService clientExecutor){
        this.balanceSocketMap = balanceSocketMap;
        this.clientExecutor = clientExecutor;
    }
    @Override
    public void run() {

    }
    private void check() {
        SalveHandle client;
        SalveSocket socket;
        for(Map.Entry<String, SalveHandle> entry : balanceSocketMap.entrySet()){
        }
    }
}
