/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.common.message;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

/**
 *
 * 回调消息
 *
 *
 * @author ElinZhou
 * @version $Id: CallBackMessage.java , v 0.1 2016/2/3 11:55 ElinZhou Exp $
 */
public class CallBackMessage extends Message {

    private CallBackMethod callBackMethod;
    private UUID           id = UUID.randomUUID();

    public CallBackMessage() {
    }

    public CallBackMessage(String message, String sender) {
        super(message, sender);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CallBackMethod getCallBackMethod() {
        return callBackMethod;
    }

    public void setCallBackMethod(CallBackMethod callBackMethod) {
        this.callBackMethod = callBackMethod;
    }

    public static void main(String... args) throws NoSuchMethodException {
        CallBackMessage callBackMessage = new CallBackMessage();
        callBackMessage.setCallBackMethod(new CallBackMethod() {
            @Override
            public void invoke(CallBackMessage callBackMessage) {
                System.out.println(callBackMessage.getId());
            }
        });
        System.out.println(callBackMessage);
        String json = callBackMessage.toString();
        Message message = Message.convertMessage(json);
        System.out.println(message);
    }
}
