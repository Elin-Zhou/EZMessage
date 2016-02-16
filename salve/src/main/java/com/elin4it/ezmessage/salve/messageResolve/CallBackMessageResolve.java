/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.salve.messageResolve;

import com.elin4it.ezmessage.common.message.CallBackMessage;
import com.elin4it.ezmessage.common.message.Message;
import com.elin4it.ezmessage.common.messageResolve.MessageResolve;
import com.elin4it.ezmessage.salve.CallBackMessageManage;

/**
 * 回调消息解析器
 * @author ElinZhou
 * @version $Id: CallBackMessageResolve.java , v 0.1 2016/2/16 13:28 ElinZhou Exp $
 */
public class CallBackMessageResolve implements MessageResolve {
    @Override
    public void resolve(Message message) {
        CallBackMessage callBackMessage = (CallBackMessage) message;
        if (callBackMessage == null) {
            return;
        }
        CallBackMessageManage.invoke(callBackMessage);
    }
}
