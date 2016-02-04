package com.elin4it.ezmessage.message;

/**
 * @author ElinZhou
 * @version $Id: SystemMessageType.java , v 0.1 2016/2/4 13:47 ElinZhou Exp $
 */
public enum SystemMessageType {
    /**
     * 心跳消息
     */
    HEART_BEAT("HEART_BEAT", "心跳消息");

    private String code;
    private String message;

    private SystemMessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
