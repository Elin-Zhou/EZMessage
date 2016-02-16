package test;

import com.elin4it.ezmessage.common.message.CallBackMessage;
import com.elin4it.ezmessage.common.message.CallBackMethod;
import com.elin4it.ezmessage.common.message.CustomMessage;
import com.elin4it.ezmessage.common.message.Message;
import com.elin4it.ezmessage.common.util.connect.InternetUtil;
import com.elin4it.ezmessage.salve.SimpleNode;
import com.elin4it.ezmessage.salve.thread.NodeServer;

public class SalveLaunch {
    public static final String IP_ADDR = "192.168.1.123";//服务器地址
    public static final int    PORT    = 12345;          //服务器端口号

    public static void main(String[] args) {
        SimpleNode simpleNode = new SimpleNode(IP_ADDR, PORT);
        simpleNode.start();
        CallBackMessage callBackMessage = new CallBackMessage();
        callBackMessage.setCallBackMethod(new CallBackMethod() {
            @Override
            public void invoke(CallBackMessage callBackMessage) {
                System.out.println(callBackMessage.getId());
            }
        });
        simpleNode.sendMessage(callBackMessage);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        while (true) {
        //            Message message = new CustomMessage("我是SALVE", InternetUtil.getMac());
        //            simpleNode.sendMessage(message);
        //            try {
        //                Thread.sleep(1000);
        //            } catch (InterruptedException e) {
        //                e.printStackTrace();
        //            }
        //        }
    }
}