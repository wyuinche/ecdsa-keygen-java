/* Copyright 2022 wyuinche
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.wuin.ecdsakeyj;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {
    public static void raiseError(String msg) {
        System.out.println(msg);
        System.exit(1);
    }

    public static void log(String msg) {
        System.out.println(msg);
    }

    public static byte[] sha3(String msg) {
        return sha3(msg.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] sha3(byte[] target) {
        try {
            MessageDigest hasher = MessageDigest.getInstance("SHA3-256");
            return hasher.digest(target);
            
        } catch(NoSuchAlgorithmException e) {
            Util.raiseError("No such algorithm; sha3-256");
        }

        return new byte[0];
    }

    public static byte[] hexStringToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
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