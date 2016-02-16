/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.salve.messageResolve;

import com.elin4it.ezmessage.salve.thread.NodeServer;
import org.apache.commons.lang.StringUtils;

import com.elin4it.ezmessage.common.message.Message;
import com.elin4it.ezmessage.common.message.SystemMessage;
import com.elin4it.ezmessage.common.messageResolve.MessageResolve;
import org.apache.log4j.Logger;

/**
 * 处理初始化数据
 *
 * @author ElinZhou
 * @version $Id: InitMessageResolve.java , v 0.1 2016/2/15 13:42 ElinZhou Exp $
 */
public class InitMessageResolve implements MessageResolve {

    private static final Logger LOGGER = Logger.getLogger(InitMessageResolve.class);
    @Override
    public void resolve(Message message) {
        try {
            SystemMessage initMessage = (SystemMessage) message;
            String id = initMessage.getMessage();
            if (StringUtils.isNotBlank(id)) {
                while (NodeServer.masterContext == null) {
                    Thread.sleep(100);
                }
                NodeServer.masterContext.setSelfId(id);
            }
        } catch (Exception e) {
            LOGGER.error("初始化消息解析异常{}",e);
        }

    }
}
