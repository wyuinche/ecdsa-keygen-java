package com.wuin.ecdsakeyj;

public class Util {
    public static void raiseError(String msg) {
        System.out.println(msg);
        System.exit(1);
    }

    public static void log(String msg) {
        System.out.println(msg);
    }

    public static byte[] hexStringToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();

        for(byte b : bytes) {
            stringBuilder.append(String.format("%02X", b & 0xff));
        }

        return stringBuilder.toString();
    }
}