/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage;

import java.util.Date;

import com.elin4it.ezmessage.thread.HeartBeatSender;
import com.elin4it.ezmessage.thread.MasterHandle;
import com.elin4it.ezmessage.util.connect.ReadThread;
import com.elin4it.ezmessage.util.connect.WriteThread;

/**
 * master连接运行上下文
 *
 * @author ElinZhou
 * @version $Id: MasterContext.java , v 0.1 2016/2/14 16:32 ElinZhou Exp $
 */
public class MasterContext {
    private String          masterId;
    private MasterHandle    masterHandle;
    private ReadThread      readerThread;
    private WriteThread     writeThread;
    private Date            lastHeartBeatTime;
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
}
