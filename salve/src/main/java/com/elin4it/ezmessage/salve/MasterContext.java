/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.salve;

import java.util.Date;

import com.elin4it.ezmessage.common.util.connect.ReadThread;
import com.elin4it.ezmessage.common.util.connect.WriteThread;
import com.elin4it.ezmessage.salve.thread.HeartBeatSender;
import com.elin4it.ezmessage.salve.thread.MasterHandle;

/**
 * master连接运行上下文
 *
 * @author ElinZhou
 * @version $Id: MasterContext.java , v 0.1 2016/2/14 16:32 ElinZhou Exp $
 */
public class MasterContext {
    /**
     * 连接的master
     */
    private String masterId;
    /**
     * 自己的Id(由master生成后返回)
     */
    private String selfId;

    /**
     * master处理线程
     */
    private MasterHandle    masterHandle;
    /**
     * 读线程
     */
    private ReadThread      readerThread;
    /**
     * 写线程
     */
    private WriteThread     writeThread;
    /**
     * 最后一次心跳发送时间
     */
    private Date            lastHeartBeatTime;
    /**
     * 心跳发送线程
     */
    private HeartBeatSender heartBeatSender;

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public MasterHandle getMasterHandle() {
        return masterHandle;
    }

    public void setMasterHandle(MasterHandle masterHandle) {
        this.masterHandle = masterHandle;
    }

    public WriteThread getWriteThread() {
        return writeThread;
    }

    public void setWriteThread(WriteThread writeThread) {
        this.writeThread = writeThread;
    }

    public Date getLastHeartBeatTime() {
        return lastHeartBeatTime;
    }

    public void setLastHeartBeatTime(Date lastHeartBeatTime) {
        this.lastHeartBeatTime = lastHeartBeatTime;
    }

    public HeartBeatSender getHeartBeatSender() {
        return heartBeatSender;
    }

    public void setHeartBeatSender(HeartBeatSender heartBeatSender) {
        this.heartBeatSender = heartBeatSender;
    }

    public ReadThread getReaderThread() {
        return readerThread;
    }

    public void setReaderThread(ReadThread readerThread) {
        this.readerThread = readerThread;
    }

    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }
}
