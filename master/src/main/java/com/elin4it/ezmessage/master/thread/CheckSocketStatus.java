/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.master.thread;

import java.util.Date;

import com.elin4it.ezmessage.master.SalveContext;
import com.elin4it.ezmessage.master.SalveContextManage;

/**
 *
 * 检查salve的连接情况
 * @author ElinZhou
 * @version $Id: CheckSocketStatus.java , v 0.1 2016/2/3 14:45 ElinZhou Exp $
 */
public class CheckSocketStatus implements Runnable {

    /**
     * 当前的所有socket
     */

    public CheckSocketStatus() {
    }

    @Override
    public void run() {
        try {
            while (true) {
                //根据salve的心跳情况来决定是否移除
                Date now;
                long diff;
                for (SalveContext salveContext : SalveContextManage.getAliveSalveContext()) {
                    now = new Date();
                    while (salveContext.getLastHeartBeatTime() == null){
                        Thread.sleep(10);
                    }
                    diff = now.getTime() - salveContext.getLastHeartBeatTime().getTime();
                    if (diff > SalveContextManage.HEART_BEAT_DELAY) {
                        //判断时间差是否已经到达断开时间
                        SalveContextManage.removeSalve(salveContext.getSalveId());
                    }
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
