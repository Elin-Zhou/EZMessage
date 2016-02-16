/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.common.message;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.elin4it.ezmessage.common.util.security.MD5Util;
import org.apache.commons.lang.StringUtils;

/**
 * @author ElinZhou
 * @version $Id: Message.java , v 0.1 2016/2/3 11:24 ElinZhou Exp $
 */
public class Message {
    private String messageType = this.getClass().getCanonicalName();
    private String sender;
    private Date   createTime;
    private String message;
    private Date   sendTime;
    private String entrypt;

    public Message() {
    }

    public Message(String message, String sender) {
        createTime = new Date();
        this.message = message;
        this.sender = sender;
    }

    /**
     * 把字符串转换成对应的Message类型
     * @param messageString
     * @return
     */
    public static Message convertMessage(String messageString) {
        Message message;
        message = JSON.parseObject(messageString, Message.class);
        //先验证数据正确性
        if (message == null) {
            return null;
        }

        if (StringUtils.equals(message.getMessageType(),
            CallBackMessage.class.getCanonicalName())) {
            message = JSON.parseObject(messageString, CallBackMessage.class);
        } else if (StringUtils.equals(message.getMessageType(),
            SystemMessage.class.getCanonicalName())) {
            message = JSON.parseObject(messageString, SystemMessage.class);
        } else if (StringUtils.equals(message.getMessageType(),
            CustomMessage.class.getCanonicalName())) {
            message = JSON.parseObject(messageString, CustomMessage.class);
        } else {
            return null;
        }
        return message;

    }

    /**
     * 检验数据是否正确
     * @param messageString
     * @return
     */
    public static boolean check(String messageString) {
        Message message;
        message = JSON.parseObject(messageString, Message.class);
        String entrypt = message.getEntrypt();
        if (StringUtils.isBlank(entrypt)) {
            return false;
        }
        String temp = message.toString();
        return StringUtils.equals(temp, messageString);
    }

    @Override
    public String toString() {
        this.entrypt = null;
        String before = JSON.toJSONString(this);
        String after = MD5Util.MD5Encode(before, "UTF-8");
        this.entrypt = after;
        return JSON.toJSONString(this);
    }

    public String getMessage() {

        return message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getEntrypt() {
        return entrypt;
    }

    public void setEntrypt(String entrypt) {
        this.entrypt = entrypt;
    }

    public void clearEntrytp() {
        this.entrypt = null;
    }

}
