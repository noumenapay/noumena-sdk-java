package com.noumena.open.api.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.noumena.open.api.dto.ActiveCardReq;
import com.noumena.open.api.dto.AttachmentReq;
import com.noumena.open.api.dto.NPayDepositReq;
import com.noumena.open.api.dto.NewCardReq;
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
public class CardTest {

    String host = "https://uat.noumena.pro";
    private static final String apiKey = "14db63d7f3614664ad1c71dd134a21dc";
    private static final String apiSecret = "ed8cb3a0-8365-4340-9d9c-33f051eedccd";
    private static final String apiPassphrase = "12345678a";


    public static final String SIGN_SEPARATOR = ":";

    @Test
    public void postNewCardTest() throws Exception {
        String timeStampStr = String.valueOf(System.currentTimeMillis());
        String method = "POST";
        String requestPath = "/api/v1/debit-cards";
        String requestQueryStr = "";

        NewCardReq req = new NewCardReq();

        req.setAcct_no("acct5000");
        req.setCard_type_id("50000001");
        req.setCust_tx_id(UUID.randomUUID().toString());


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
    public void postActivateCardAttachmentTest() throws Exception {
        String timeStampStr = String.valueOf(System.currentTimeMillis());
        String method = "POST";
        String requestPath = "/api/v1/debit-cards/attachment";
        String requestQueryStr = "";

        AttachmentReq req = new AttachmentReq();

        req.setCard_no("4366520800000050100");
        String imgBase64 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADMAAAAyCAMAAADGIxO9AAABdFBMVEUAAAAA//+AgP8zmf9Jkv9Vqv9Nmf9Gov9Aqv9Onf9JpP9Eqv9Qn/9Lpf9Hqv9Dof9Npv9Jnv9Gov9Dpv9Kn/9Ho/9Fp/9Mof9JpP9Gp/9In/9Lpf9Ho/9HoP9KpP9Jof9Hpf9Kov9Go/9Iov9Ho/9Jov9Iov9JpP9Iov9Ho/9Jov9Hof9Iov9Jo/9IpP9Jo/9Iov9Ho/9Iov9Io/9Iov9JpP9Iov9Ho/9Io/9IpP9Hov9IpP9JpP9Io/9Io/9Io/9Io/9Hov9Io/9IpP9Ho/9Jov9Io/9Io/9Io/9IpP9Io/9Io/9Io/9Ho/9Iov9Io/9Io/9Jo/9Io/9Io/9Ho/9Io/9Io/9Io/9Jo/9IpP9Io/9Ho/9Iov9Io/9Io/9Jo/9Io/9IpP9Io/9Jo/9Io/9Io/9Io/9Io/9Jo/9Io/9Io/9Io/9Io/9Io/9Io/9Io/9Io/9Io/9Io/9Io/9Io/9Io/9Io/9Io/9Io/9Io/9Io/////9nZg6jAAAAenRSTlMAAQIFBwkKCwwNDg8QERITFBUWFxgZGhscHSAiJCstMTY3Ojw9QkdJSktNT1VbXF5gYWNneXt8fYCDhIaJipucpKirra+ztLW3uL3AwsjJysvMzc7P0NHS09TV1tfY2drb3+Dh5OXm5+jp6uvs7vHz9PX3+Pn6+/z9/pduSYEAAAABYktHRHtP0rX8AAACGElEQVRIx5XWWVvTQBQG4K+oiG1jm2ap1n0XRa0b1gVRWVxwAWwbaJZaQBAxuGLw/HovKjqTzILnKk9y3nyTZCbPANnKHbs5PvM+juM4nBq7bGYb7PSJo/d6xNb2i+uDqRafRyeebFOmPt4pcE3EImtcIIiI1m8MsIZ6tZ3jkU2S1ssqayhw+o/e+EWKWj3FGlqqAdjziNT1fYQ1FDpA8a3G0NYwayh0gVJTh76eYQ0t1YDCKx3acFlDgbObpKc51uwyqc4ZCmyg1NKYlSHOUK8GFF5r0Chv6N0hPVof5A35FlBuq9GVlKFuFSiqk56lDXUswPSUs+FA2vST3qjQhYyhRV3S3azRJk0IDC1UgMqC1LREhrouUJyVmTWhoUiFPosNeSZQWRRfSySGPBOwOhLzTYIiFzBmxWZD9qihCxhzQtORvtN2GbB8kZmUfzxJUoL7ilnSKgO2nzXnVdMxdACznTH5LRVqlgA7SBtMkTbJS5tr6qXcLAFOkDJ7V9UoSCclAG5p/k6BzaMEQP6DBs0fBNyQM7hK/5WUAMDAcx2aNwA3Yg2Mng759r/l3jc4+UWH5gyg2mUNhn9qUR6oRqzBuU2NeZDDn+H9NTi9phLJ7X6XG7EGpmLiLZ/d6XIj1gCXliUbg8Y+5tYeZ1BsfBIM6/FxrsntpHZZ++vTP/ilOnpEu3sDhi6OTUdxHMcrMw/rhwVbQvwGOaukvpva6/sAAAAASUVORK5CYII=";
        req.setActive_doc(imgBase64);
        //req.setPoa_doc("");


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
    public void getCardsTest() throws Exception {
        String timeStampStr = String.valueOf(System.currentTimeMillis());
        String method = "GET";
        String requestPath = "/api/v1/debit-cards";
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
    public void postActiveCardTest() throws Exception {
        String timeStampStr = String.valueOf(System.currentTimeMillis());
        String method = "PUT";
        String requestPath = "/api/v1/debit-cards/status";
        String requestQueryStr = "";

        ActiveCardReq req = new ActiveCardReq();

        req.setAcct_no("acct001");
        req.setCard_no("822848003056012011");

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
                .put(body)
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
