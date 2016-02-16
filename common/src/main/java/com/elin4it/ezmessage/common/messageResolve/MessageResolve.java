package com.elin4it.ezmessage.common.messageResolve;

import com.elin4it.ezmessage.common.message.Message;

/**
 * @author ElinZhou
 * @version $Id: messageResolve.java , v 0.1 2016/2/4 11:39 ElinZhou Exp $
 */
public interface MessageResolve {
    void resolve(Message message);
}
