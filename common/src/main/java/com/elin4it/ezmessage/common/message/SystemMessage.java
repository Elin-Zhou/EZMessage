/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.common.message;

/**
 * @author ElinZhou
 * @version $Id: SystemMessage.java , v 0.1 2016/2/4 12:38 ElinZhou Exp $
 */
public class SystemMessage extends Message {
    public SystemMessageType systemMessageType;

    public SystemMessage() {
    }

    public SystemMessage(String message, String sender, SystemMessageType systemMessageType) {
        super(message, sender);
        this.systemMessageType = systemMessageType;
    }

    public SystemMessageType getSystemMessageType() {
        return systemMessageType;
    }

    public void setSystemMessageType(SystemMessageType systemMessageType) {
        this.systemMessageType = systemMessageType;
    }
}
