/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package test;

import com.elin4it.ezmessage.messageResolve.MessageResolve;
import com.elin4it.ezmessage.messageResolve.SimpleMessageResolve;
import com.elin4it.ezmessage.SimperReceiver;
import com.elin4it.ezmessage.message.CustomMessage;
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
        MessageResolve messageResolve = new SimpleMessageResolve();
        receiver.setMessageResolve(messageResolve);
        receiver.start();
        //        try {
        //            Thread.sleep(5000);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //        receiver.pause();
        while (true) {
            Message message = new CustomMessage("你好你好", "MASTER");
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
