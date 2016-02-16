/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.salve.thread;

import java.util.Date;

import org.apache.log4j.Logger;

import com.elin4it.ezmessage.common.message.SystemMessage;
import com.elin4it.ezmessage.common.message.SystemMessageType;

/**
 * 心跳发送线程
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
    private static final SystemMessage hearBeatMessage = new SystemMessage("HEART_BEAT",
        NodeServer.masterContext.getSelfId(), SystemMessageType.HEART_BEAT);

    @Override
    public void run() {
        try {
            /**
             * 心跳发送原理
             *
             * 定期向master发送一条类型为HEART_BEAT的系统消息，master将解析为心跳包
             */

            while (true) {
                NodeServer.masterContext.getMasterHandle().sendMessage(hearBeatMessage);
                NodeServer.masterContext.setLastHeartBeatTime(new Date());
                Thread.sleep(HEAR_SEND_DELAY);
            }
        } catch (Exception e) {
            LOGGER.error("发送心跳数据异常{}", e);
        }

    }
}
