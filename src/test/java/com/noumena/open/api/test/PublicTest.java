package com.noumena.open.api.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.noumena.open.api.dto.CalcCryptoReq;
import com.noumena.open.api.dto.CalcCurrencyReq;
import com.noumena.open.api.dto.DepositReq;
import com.noumena.open.api.util.HmacSHA256Base64Util;
import com.noumena.open.api.util.HttpUtil;
import okhttp3.*;
import org.junit.Before;
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
public class PublicTest {

    String host = "https://uat.noumena.pro";
    private static final String apiKey = "14db63d7f3614664ad1c71dd134a21dc";
    private static final String apiSecret = "ed8cb3a0-8365-4340-9d9c-33f051eedccd";
    private static final String apiPassphrase = "12345678a";

    @Before
    public void setUp() throws Exception {
        HttpUtil.init(host,apiKey,apiSecret,apiPassphrase);
    }


}
