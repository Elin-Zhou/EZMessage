/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package test;

import com.elin4it.ezmessage.SimperReceiver;
import com.elin4it.ezmessage.message.Message;

/**
 * 工程启动入口
 *
 * @author ElinZhou
 * @version $Id: MasterLaunch.java , v 0.1 2016/2/2 13:18 ElinZhou Exp $
 */
public class MasterLaunch {
    public static void main(String[] args) {
        SimperReceiver receiver = new SimperReceiver(12345);
        receiver.start();
        //        try {
        //            Thread.sleep(5000);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //        receiver.pause();
        while (true) {
            Message message = new Message(System.currentTimeMillis() + "你好你好");
            receiver.sendMessage(message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //        try {
        //            Thread.sleep(5000);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //        receiver.goOn();
    }
}
