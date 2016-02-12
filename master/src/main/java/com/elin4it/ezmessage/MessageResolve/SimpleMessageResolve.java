/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.MessageResolve;

import com.elin4it.ezmessage.message.Message;

/**
 * @author ElinZhou
 * @version $Id: SimpleMessageResolve.java , v 0.1 2016/2/4 12:53 ElinZhou Exp $
 */
public class SimpleMessageResolve implements MessageResolve {
    @Override
    public void resolve(Message message) {
        System.out.println(message);
    }
}
