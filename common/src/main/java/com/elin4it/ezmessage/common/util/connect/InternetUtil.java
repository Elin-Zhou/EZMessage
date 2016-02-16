/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.ezmessage.common.util.connect;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * 网络工具类
 *
 * @author ElinZhou
 * @version $Id: InternetUtil.java , v 0.1 2016/2/15 14:57 ElinZhou Exp $
 */
public class InternetUtil {

    /**
     * 获取IP地址
     * @return
     */
    public static String getMac() {
        try {
            // 获得ＩＰ
            NetworkInterface netInterface = NetworkInterface
                .getByInetAddress(InetAddress.getLocalHost());
            // 获得Mac地址的byte数组
            byte[] macAddr = netInterface.getHardwareAddress();
            // 循环输出
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : macAddr) {
                // 这里的toHexString()是自己写的格式化输出的方法，见下步。
                stringBuilder.append(toHexString(b));
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return null;
        }

    }

    private static String toHexString(int integer) {
        // 将得来的int类型数字转化为十六进制数
        String str = Integer.toHexString((int) (integer & 0xff));
        // 如果遇到单字符，前置0占位补满两格
        if (str.length() == 1) {
            str = "0" + str;
        }
        return str;
    }

    public static void main(String... args) throws Exception {
        System.out.println(getMac());
    }
}
