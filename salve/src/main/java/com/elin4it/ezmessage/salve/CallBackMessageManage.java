/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.salve;

import com.elin4it.ezmessage.common.message.CallBackMessage;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ElinZhou
 * @version $Id: CallBackMessageManage.java , v 0.1 2016/2/16 13:21 ElinZhou Exp $
 */
public class CallBackMessageManage {
    private static ConcurrentHashMap<UUID, CallBackMessage> messages = new ConcurrentHashMap<UUID, CallBackMessage>();

    public static void notify(CallBackMessage callBackMessage) {
        messages.put(callBackMessage.getId(), callBackMessage);
    }

    public static boolean invoke(CallBackMessage callBackMessage) {
        if (callBackMessage == null) {
            return false;
        }
        CallBackMessage realMessage = messages.get(callBackMessage.getId());
        if (realMessage == null) {
            return false;
        }

        realMessage.getCallBackMethod().invoke(callBackMessage);
        return true;
    }
}
