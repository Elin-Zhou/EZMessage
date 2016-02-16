package com.elin4it.ezmessage.common.message;

/**
 * 回调消息调用方法接口
 *
 * @author ElinZhou
 * @version $Id: CallBackMethod.java , v 0.1 2016/2/16 13:14 ElinZhou Exp $
 */
public interface CallBackMethod {
    void invoke(CallBackMessage callBackMessage);
}
