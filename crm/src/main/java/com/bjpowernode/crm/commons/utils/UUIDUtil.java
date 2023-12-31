package com.bjpowernode.crm.commons.utils;

import java.util.UUID;

/**
 * @version: java version 1.8
 * @Author: Zerroi
 * @description:
 * @date: 2023-09-14 22:24
 */
public class UUIDUtil {

    /**
     * 获得4个长度的十六进制的UUID
     *
     * @return UUID
     */
    public static String get4UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[1];
    }

    /**
     * 获得8个长度的十六进制的UUID
     *
     * @return UUID
     */
    public static String get8UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0];
    }

    /**
     * 获得12个长度的十六进制的UUID
     *
     * @return UUID
     */
    public static String get12UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0] + idd[1];
    }

    /**
     * 获得16个长度的十六进制的UUID
     *
     * @return UUID
     */
    public static String get16UUID() {

        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0] + idd[1] + idd[2];
    }

    /**
     * 获得20个长度的十六进制的UUID
     *
     * @return UUID
     */
    public static String get20UUID() {

        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0] + idd[1] + idd[2] + idd[3];
    }

    /**
     * 获得24个长度的十六进制的UUID
     *
     * @return UUID
     */
    public static String get24UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0] + idd[1] + idd[4];
    }

    /**
     * 获得32个长度的十六进制的UUID
     *
     * @return UUID
     */
    public static String get32UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0] + idd[1] + idd[2] + idd[3] + idd[4];
    }
}


