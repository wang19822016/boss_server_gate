package com.seastar.utils;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by wjl on 2016/6/3.
 */
public class Utils {

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static boolean validateDeviceId(String deviceId) {
        if (deviceId == null || deviceId.isEmpty() || !deviceId.matches("[a-zA-Z0-9-_:]{5,50}"))
            return false;
        return true;
    }

    public static boolean validateThirdPartyUserId(String id) {
        if (id == null || id.isEmpty() || !id.matches("[a-zA-Z0-9]{1,60}"))
            return false;

        return true;
    }

    public static boolean validateEmail(String email) {
        if (email == null || email.isEmpty() || !email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"))
            return false;

        return true;
    }

    public static boolean validateUserName(String name) {
        if (name == null || name.isEmpty() || !name.matches("[a-zA-Z][a-zA-Z0-9]{5,22}"))
            return false;

        return true;
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.isEmpty() || !password.matches("[a-zA-Z0-9]{8,32}"))
            return false;

        return true;
    }

    public static boolean validateGameRoleId(String id) {
        if (id == null || id.isEmpty() || !id.matches("[a-zA-Z0-9]{1,60}"))
            return false;

        return true;
    }

    public static boolean validateServerId(String id) {
        if (id == null)
            return false;

        if (id.isEmpty())
            return true;

        if (!id.matches("[a-zA-Z0-9]{1,10}"))
            return false;

        return true;
    }

    public static String md5encode(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
                'f' };
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(s.getBytes());
            byte[] bytes = md5.digest();

            StringBuffer stringbuffer = new StringBuffer(2 * bytes.length);
            for (int l = 0; l < bytes.length; l++) {
                char c0 = hexDigits[(bytes[l] & 0xf0) >> 4];
                char c1 = hexDigits[bytes[l] & 0xf];
                stringbuffer.append(c0);
                stringbuffer.append(c1);
            }
            return stringbuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String b64encode(String s) {
        s = s.trim();
        if (s.isEmpty())
            return s;
        return Base64.getEncoder().encodeToString(s.getBytes());
    }

    public static String b64decode(String s) {
        if (s.isEmpty())
            return s;
        return new String(Base64.getDecoder().decode(s));
    }

    public static String sha256encode(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(s.getBytes());
            byte[] result = md.digest();

            String des = "";
            String tmp = null;
            for (int i = 0; i < result.length; i++) {
                tmp = (Integer.toHexString(result[i] & 0xFF));
                if (tmp.length() == 1) {
                    des += "0";
                }
                des += tmp;
            }

            return des;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
