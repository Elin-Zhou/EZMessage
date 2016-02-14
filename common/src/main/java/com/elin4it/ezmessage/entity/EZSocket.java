/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.entity;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * @author ElinZhou
 * @version $Id: EZSocket.java , v 0.1 2016/2/2 14:03 ElinZhou Exp $
 */
public class EZSocket {
    private String id;
    private Date   connectDate;
    private Socket socket;

    public EZSocket(Socket socket) {
        this.socket = socket;
        this.id = newId();
        this.connectDate = new Date();
    }

    public EZSocket(String address, Integer port) throws IOException {
        this(new Socket(address, port));
    }

    private String newId() {
        StringBuilder stringBuilder = new StringBuilder();
        String ip = socket.getInetAddress().getHostAddress().replace(".", "");
        stringBuilder.append(System.currentTimeMillis()).append(ip);
        return stringBuilder.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
