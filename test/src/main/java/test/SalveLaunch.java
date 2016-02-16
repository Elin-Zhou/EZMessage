package test;

import com.elin4it.ezmessage.common.message.CustomMessage;
import com.elin4it.ezmessage.common.message.Message;
import com.elin4it.ezmessage.common.util.connect.InternetUtil;
import com.elin4it.ezmessage.salve.SimpleNode;

public class SalveLaunch {
    public static final String IP_ADDR = "192.168.1.123";//服务器地址
    public static final int    PORT    = 12345;          //服务器端口号

    public static void main(String[] args) {
        SimpleNode simpleNode = new SimpleNode(IP_ADDR, PORT);
        simpleNode.start();
        while (true) {
            Message message = new CustomMessage("我是SALVE", InternetUtil.getMac());
            simpleNode.sendMessage(message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}