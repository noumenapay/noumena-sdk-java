package com.noumena.open.api.test;

import com.alibaba.fastjson.JSONObject;
import com.noumena.open.api.dto.AccountKycReq;
import com.noumena.open.api.dto.NPayDepositReq;
import com.noumena.open.api.enums.CoinTypeEnum;
import com.noumena.open.api.util.HmacSHA256Base64Util;
import okhttp3.*;
import org.junit.Test;

import java.util.Date;
import java.util.TreeMap;
import java.util.UUID;

/**
 * @author zhouq
 * @version 1.0
 * @date 2019/12/11
 */
public class KycTest {

    String host = "https://uat.noumena.pro";
    private static final String apiKey = "14db63d7f3614664ad1c71dd134a21dc";
    private static final String apiSecret = "ed8cb3a0-8365-4340-9d9c-33f051eedccd";
    private static final String apiPassphrase = "12345678a";


    public static final String SIGN_SEPARATOR = ":";

    @Test
    public void postKycTest() throws Exception {
        String timeStampStr = String.valueOf(System.currentTimeMillis());
        String method = "POST";
        String requestPath = "/api/v1/customers/accounts";
        String requestQueryStr = "";

        AccountKycReq req = new AccountKycReq();
        req.setMail("test01@noumena.pro");
        req.setAcctNo("acct001");
        req.setAcctName("test name001");
        req.setFirstName("micky");
        req.setLastName("Wang");
        req.setMobile("1582170000");
        req.setGender("male");
        req.setBirthday("1990-01-01");
        req.setCity("moon");
        req.setState("moon");
        req.setCountry("A");
        req.setNationality("A");
        req.setDocNo("1234");
        req.setDocType("1");
        req.setFrontDoc("no");
        req.setBackDoc("no");
        req.setMixDoc("no");
        req.setCountryCode("96");
        req.setAddress("street 1");
        req.setZipcode("123456");
        req.setMaidenName("mom");
        req.setBankId("1002");
//        req.setMailVerificationCode("123456"); //optional
//        req.setMailToken("123456");  //optional

        TreeMap<String,String> map = JSONObject.parseObject(req.toString(),TreeMap.class);

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
    public void getAccountsKycTest() throws Exception {
        String timeStampStr = String.valueOf(System.currentTimeMillis());
        String method = "GET";
        String requestPath = "/api/v1/customers/accounts";
        String requestQueryStr = "page_num=1&page_size=20&former_time=1578565459&latter_time=1579429459&time_sort=asc";

        String sign = HmacSHA256Base64Util.sign(timeStampStr, method, requestPath,  requestQueryStr, apiKey, apiSecret, null);

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
        Request request = new Request.Builder()
                .url(host+requestPath+"?"+requestQueryStr)
                .get()
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
    public void getAccountKycStatusTest() throws Exception {
        String timeStampStr = String.valueOf(System.currentTimeMillis());
        String method = "GET";
        String requestPath = "/api/v1/customers/accounts";
        String requestQueryStr = "acct_no=acct001";

        String sign = HmacSHA256Base64Util.sign(timeStampStr, method, requestPath,  requestQueryStr, apiKey, apiSecret, null);

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
        Request request = new Request.Builder()
                .url(host+requestPath+"?"+requestQueryStr)
                .get()
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