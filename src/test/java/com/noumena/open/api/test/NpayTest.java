package com.noumena.open.api.test;

import com.alibaba.fastjson.JSONObject;
import com.noumena.open.api.dto.NPayDepositReq;
import com.noumena.open.api.enums.CoinTypeEnum;
import com.noumena.open.api.util.HmacSHA256Base64Util;
import okhttp3.*;
import org.junit.Test;

import java.util.TreeMap;
import java.util.UUID;

/**
 * @author zhouq
 * @version 1.0
 * @date 2019/12/11
 */
public class NpayTest {

    String host = "https://uat.noumena.pro";
    private static final String apiKey = "cdb780f1afc64e57a32928608b78cb11";
    private static final String apiSecret = "fa47e3af-4e38-4688-b8dc-f03472a2fadb";
    private static final String apiPassphrase = "12345678a";


    public static final String SIGN_SEPARATOR = ":";



    @Test
    public void getNpayTxTest() throws Exception {
        String timeStampStr = String.valueOf(System.currentTimeMillis());
        String method = "GET";
        String requestPath = "/api/v1/npay/cust/transaction";
        String requestQueryStr = "page_num=1&page_size=20";

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
    public void postDepositTest() throws Exception {
        String timeStampStr = String.valueOf(System.currentTimeMillis());
        String method = "POST";
        String requestPath = "/api/v1/npay/cust/transaction";
        String requestQueryStr = "";

        NPayDepositReq req = new NPayDepositReq();

        req.setAcct_no("did:ont:ALaRqCkXSWaHMDc5sLEEMVMWqCNDFi5eRZ");
        req.setCust_user_no("mw123");
        req.setCust_tx_id(UUID.randomUUID().toString());
        req.setCoin_type(CoinTypeEnum.PAX.value());
        req.setTx_amount("10");
        req.setBonus_coin_type(CoinTypeEnum.ONT.value());
        req.setBonus_tx_amount("1");
        req.setRemark("test deposit");

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
}
