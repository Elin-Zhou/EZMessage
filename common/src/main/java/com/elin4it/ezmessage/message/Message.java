/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.message;

import com.alibaba.fastjson.JSON;
import com.elin4it.ezmessage.util.security.MD5Util;

import java.util.Date;

/**
 * @author ElinZhou
 * @version $Id: Message.java , v 0.1 2016/2/3 11:24 ElinZhou Exp $
 */
public class Message {
    private String messageType = this.getClass().getCanonicalName();
    private Date   createTime;
    private String message;
    private Date   sendTime;
    private String entrypt;

    public Message(String message) {
        createTime = new Date();
        this.message = message;
    }

    @Override
    public String toString() {
        entrypt = null;
        String before =  JSON.toJSONString(this);
        String after = MD5Util.MD5Encode(before, "UTF-8");
        entrypt = after;
        return JSON.toJSONString(this);
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getEntrypt() {
        return entrypt;
    }

    public static void main(String... args) {
        Message message = new Message("message");
        message.setSendTime(new Date());
        System.out.println(message);
    }
}
