/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.thread;

import com.elin4it.ezmessage.message.SystemMessage;
import com.elin4it.ezmessage.message.SystemMessageType;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * 心跳发送
 *
 * @author ElinZhou
 * @version $Id: HeartBeatSender.java , v 0.1 2016/2/14 16:50 ElinZhou Exp $
 */
public class HeartBeatSender implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(HeartBeatSender.class);

    /**
     * 心跳发送时延，默认2000ms
     */
    private static final int HEAR_SEND_DELAY = 2000;

    /**
     * 心跳信息
     */
    private static final SystemMessage hearBeatMessage = new SystemMessage("", "",
        SystemMessageType.HEART_BEAT);

    @Override
    public void run() {
        try {
            while (true) {
                NodeServer.masterContext.getMasterHandle().sendMessage(hearBeatMessage);
                NodeServer.masterContext.setLastHeartBeatTime(new Date());
                LOGGER.info("发送心跳");
                Thread.sleep(HEAR_SEND_DELAY);
            }
        } catch (Exception e) {
            LOGGER.error("发送心跳数据异常{}",e);
        }

    }
}
