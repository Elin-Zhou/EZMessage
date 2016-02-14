/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.messageResolve;

import com.elin4it.ezmessage.SalveContextManage;
import com.elin4it.ezmessage.message.Message;

import java.util.Date;

/**
 * @author ElinZhou
 * @version $Id: HeartBeatMessageResolve.java , v 0.1 2016/2/4 13:45 ElinZhou Exp $
 */
public class HeartBeatMessageResolve implements MessageResolve {
    /**
     * 心跳间隔默认允许最大时间差
     */
    private static final long DELAY = 5000;

    @Override
    public void resolve(Message message) {
        //修改salve的最后心跳时间
        SalveContextManage.setLastHeartBeatTime(message.getSender(), new Date());
    }
}
