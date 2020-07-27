package com.shinedi.javabase.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @Author: D-S
 * @Date: 2020/7/26 6:25 下午
 */
public class RsaUtil {
    public static final String SIGN_ALGORITHMS = "SHA256withRSA";

    public RsaUtil() {
    }

    public static String doGet(String url, Map<String, Object> data, String privateKey) throws Exception {
        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);
        LocalDateTime localDateTime = LocalDateTime.now();
        long requestTime = localDateTime.toEpochSecond(zoneOffset);
        data.put("time_stamp", requestTime);
        String sign = getSign(data, privateKey);
        url = url + "?" + appendParam(data);
        URL localURL = new URL(url);
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        httpURLConnection.setRequestProperty("Sign", sign);
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        if (httpURLConnection.getResponseCode() >= 300) {
            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
        } else {
            try {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);

                while((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                }
            } finally {
                if (reader != null) {
                    reader.close();
                }

                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }

                if (inputStream != null) {
                    inputStream.close();
                }

            }

            return resultBuffer.toString();
        }
    }

    public static String getSign(Map<String, Object> map, String privateKey1) {
        String param = appendParam(map);
        String content = crypt(param);
        return ((String) Objects.requireNonNull(sign(content, privateKey1))).replace("\n", "");
    }

    public static String sign(String content, String privateKey1) {
        try {
            PrivateKey privateKey = string2PrivateKey(privateKey1);
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(content.getBytes("utf-8"));
            byte[] signed = signature.sign();
            return (new BASE64Encoder()).encode(signed);
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static PrivateKey string2PrivateKey(String priStr) throws Exception {
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static RSAPrivateKey getPrivateKey(String priStr) throws Exception {
        ByteBuffer keyBytes = (new BASE64Decoder()).decodeBufferToByteBuffer(priStr);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keyBytes.array());
        KeyFactory kf = KeyFactory.getInstance("RSA", "IBMJCEFIPS");
        RSAPrivateKey pk = (RSAPrivateKey)kf.generatePrivate(privateKeySpec);
        return pk;
    }

    public static byte[] base642Byte(String base64Key) throws IOException {
        String base = base64Key.replace("\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base);
    }

    private static String appendParam(Map<String, Object> map) {
        map = sortMapByKey(map);
        StringBuffer str = new StringBuffer();
        String strr = "";
        Iterator var3 = map.keySet().iterator();

        while(var3.hasNext()) {
            String key = (String)var3.next();
            str.append(key + "=" + map.get(key));
            str.append("&");
        }

        if (str.length() > 1) {
            strr = str.toString().substring(0, str.toString().length() - 1);
        }

        return strr;
    }

    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        return map != null && !map.isEmpty() ? new TreeMap(map) : null;
    }

    public static String crypt(String str) {
        if (str != null && str.length() != 0) {
            StringBuffer hexString = new StringBuffer();

            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(str.getBytes());
                byte[] hash = md.digest();

                for(int i = 0; i < hash.length; ++i) {
                    if ((255 & hash[i]) < 16) {
                        hexString.append("0" + Integer.toHexString(255 & hash[i]));
                    } else {
                        hexString.append(Integer.toHexString(255 & hash[i]));
                    }
                }
            } catch (NoSuchAlgorithmException var5) {
                var5.printStackTrace();
            }

            return hexString.toString();
        } else {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }
    }
}
