/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.common.message;

import java.util.Date;

/**
 * @author ElinZhou
 * @version $Id: CallBackMessage.java , v 0.1 2016/2/3 11:55 ElinZhou Exp $
 */
public class CallBackMessage extends Message {
    private String objectId;

    public CallBackMessage() {
    }
    public CallBackMessage(String message, String sender) {
        super(message, sender);
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public static void main(String... args) {
        CallBackMessage message = new CallBackMessage("message","MASTER");
        message.setSendTime(new Date());
        message.setObjectId("112");
        System.out.println(message);
    }
}
