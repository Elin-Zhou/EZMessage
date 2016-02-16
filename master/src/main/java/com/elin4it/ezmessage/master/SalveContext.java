/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.master;

import java.util.Date;
import java.util.concurrent.Future;

import com.elin4it.ezmessage.common.util.connect.ReadThread;
import com.elin4it.ezmessage.common.util.connect.WriteThread;
import com.elin4it.ezmessage.master.thread.SalveHandle;

/**
 * 从节点运行的上下文信息
 * @author ElinZhou
 * @version $Id: SalveContext.java , v 0.1 2016/2/3 15:02 ElinZhou Exp $
 */
public class SalveContext {

    private String              salveId;
    private String              selfId;
    private SalveHandle salveHandle;
    private Future<SalveHandle> salveHandleFuture;
    private Future<ReadThread>  readThreadFuture;
    private WriteThread         writeThread;
    private Date                lastHeartBeatTime;

    public SalveContext(String salveId) {
        this.salveId = salveId;
    }

    public String getSalveId() {
        return salveId;
    }

    public Future<SalveHandle> getSalveHandleFuture() {
        return salveHandleFuture;
    }

    public Future<ReadThread> getReadThreadFuture() {
        return readThreadFuture;
    }

    public void setSalveHandleFuture(Future<SalveHandle> salveHandleFuture) {
        this.salveHandleFuture = salveHandleFuture;
    }

    public void setReadThreadFuture(Future<ReadThread> readThreadFuture) {
        this.readThreadFuture = readThreadFuture;
    }

    public WriteThread getWriteThread() {
        return writeThread;
    }

    public void setWriteThread(WriteThread writeThread) {
        this.writeThread = writeThread;
    }

    public SalveHandle getSalveHandle() {
        return salveHandle;
    }

    public void setSalveHandle(SalveHandle salveHandle) {
        this.salveHandle = salveHandle;
    }

    public Date getLastHeartBeatTime() {
        return lastHeartBeatTime;
    }

    public void setLastHeartBeatTime(Date lastHeartBeatTime) {
        this.lastHeartBeatTime = lastHeartBeatTime;
    }

    public void setSalveId(String salveId) {
        this.salveId = salveId;
    }
}
