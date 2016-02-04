/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage;

import java.net.Socket;
import java.util.Date;

/**
 * @author ElinZhou
 * @version $Id: SalveSocket.java , v 0.1 2016/2/2 14:03 ElinZhou Exp $
 */
public class SalveSocket {
    private String salveId;
    private Date   connectDate;
    private Socket socket;

    public SalveSocket(Socket socket) {
        this.socket = socket;
        this.salveId = newId();
        this.connectDate = new Date();
    }

    private String newId() {
        StringBuilder stringBuilder = new StringBuilder();
        String ip = socket.getInetAddress().getHostAddress().replace(".", "");
        stringBuilder.append(System.currentTimeMillis()).append(ip);
        return stringBuilder.toString();
    }

    public String getSalveId() {
        return salveId;
    }

    public void setSalveId(String salveId) {
        this.salveId = salveId;
    }

    public Date getConnectDate() {
        return connectDate;
    }

    public void setConnectDate(Date connectDate) {
        this.connectDate = connectDate;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
