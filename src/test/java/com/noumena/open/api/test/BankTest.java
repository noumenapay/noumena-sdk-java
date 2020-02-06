package com.noumena.open.api.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.noumena.open.api.dto.DepositReq;
import com.noumena.open.api.util.HmacSHA256Base64Util;
import okhttp3.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * @author zhouq
 * @version 1.0
 * @date 2019/12/11
 */
public class BankTest {

    String host = "https://uat.noumena.pro";
    private static final String apiKey = "14db63d7f3614664ad1c71dd134a21dc";
    private static final String apiSecret = "ed8cb3a0-8365-4340-9d9c-33f051eedccd";
    private static final String apiPassphrase = "12345678a";


    public static final String SIGN_SEPARATOR = ":";

    @Test
    public void getBankCardActiveTest() throws Exception {
        String timeStampStr = String.valueOf(System.currentTimeMillis());
        String method = "POST";
        String requestPath = "/api/v1/bank/account-status";
        String requestQueryStr = "";

        Map req = new HashMap<>();

        req.put("card_no","822848003056012013");

        TreeMap<String,String> map = JSONObject.parseObject(JSON.toJSONString(req),TreeMap.class);

        String sign = HmacSHA256Base64Util.sign(timeStampStr, method, requestPath,  requestQueryStr, apiKey, apiSecret, map);

        String authorizationStr = "Noumena"
                + SIGN_SEPARATOR
                + apiKey
                +SIGN_SEPARATOR
                + timeStampStr
                +SIGN_SEPARATOR
                + sign;
        System.out.println();
        System.out.println("Authorization:"+authorizationStr);
        System.out.println("Access-Passphrase:"+apiPassphrase);

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, JSONObject.toJSONString(req));
        Request request = new Request.Builder()
                .url(host+requestPath)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization",authorizationStr)
                .addHeader("Access-Passphrase",apiPassphrase)
                .build();
        Response response = client.newCall(request).execute();
        //System.out.println("result="+response.isSuccessful());
        System.out.println();
        if (response.isSuccessful()) {
            System.out.println(response.body().string());
        }else{
            System.out.println("error... " + response.body().string());
        }
    }



    @Test
    public void getBankCardBalanceTest() throws Exception {
        String timeStampStr = String.valueOf(System.currentTimeMillis());
        String method = "POST";
        String requestPath = "/api/v1/bank/balance";
        String requestQueryStr = "";

        Map req = new HashMap<>();

        req.put("card_no","822848003056012013");

        TreeMap<String,String> map = JSONObject.parseObject(JSON.toJSONString(req),TreeMap.class);

        String sign = HmacSHA256Base64Util.sign(timeStampStr, method, requestPath,  requestQueryStr, apiKey, apiSecret, map);

        String authorizationStr = "Noumena"
                + SIGN_SEPARATOR
                + apiKey
                +SIGN_SEPARATOR
                + timeStampStr
                +SIGN_SEPARATOR
                + sign;
        System.out.println();
        System.out.println("Request Url:"+ host+requestPath+"?"+requestQueryStr );
        System.out.println("Authorization:"+authorizationStr);
        System.out.println("Access-Passphrase:"+apiPassphrase);

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, JSONObject.toJSONString(req));
        Request request = new Request.Builder()
                .url(host+requestPath)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization",authorizationStr)
                .addHeader("Access-Passphrase",apiPassphrase)
                .build();
        Response response = client.newCall(request).execute();
        //System.out.println("result="+response.isSuccessful());
        System.out.println();
        if (response.isSuccessful()) {
            System.out.println(response.body().string());
        }else{
            System.out.println("error... " + response.body().string());
        }
    }


    @Test
    public void getBankTransactionRecordTest() throws Exception {
        String timeStampStr = String.valueOf(System.currentTimeMillis());
        String method = "POST";
        String requestPath = "/api/v1/bank/transaction-record";
        String requestQueryStr = "";

        Map req = new HashMap<>();

        req.put("card_no","822848003056012013");
        //req.put("month_year","012020");
        req.put("former_month_year","012020");
        req.put("latter_month_year","012020");

        TreeMap<String,String> map = JSONObject.parseObject(JSON.toJSONString(req),TreeMap.class);

        String sign = HmacSHA256Base64Util.sign(timeStampStr, method, requestPath,  requestQueryStr, apiKey, apiSecret, map);

        String authorizationStr = "Noumena"
                + SIGN_SEPARATOR
                + apiKey
                +SIGN_SEPARATOR
                + timeStampStr
                +SIGN_SEPARATOR
                + sign;
        System.out.println();
        System.out.println("Request Url:"+ host+requestPath+"?"+requestQueryStr );
        System.out.println("Authorization:"+authorizationStr);
        System.out.println("Access-Passphrase:"+apiPassphrase);

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, JSONObject.toJSONString(req));
        Request request = new Request.Builder()
                .url(host+requestPath)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization",authorizationStr)
                .addHeader("Access-Passphrase",apiPassphrase)
                .build();
        Response response = client.newCall(request).execute();
        //System.out.println("result="+response.isSuccessful());
        System.out.println();
        if (response.isSuccessful()) {
            System.out.println(response.body().string());
        }else{
            System.out.println("error... " + response.body().string());
        }
    }

}
