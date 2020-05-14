package com.noumena.open.api.test;

import com.alibaba.fastjson.JSON;
import com.github.ontio.sdk.manager.ECIES;
import com.noumena.open.api.dto.BankBalanceReq;
import com.noumena.open.api.dto.BankTxRecordsReq;
import com.noumena.open.api.util.HttpUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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


    @Before
    public void setUp() throws Exception {
        HttpUtil.init(host,apiKey,apiSecret,apiPassphrase);
    }

    @Test
    public void getBankCardActiveTest() throws Exception {

        String requestPath = "/api/v1/bank/account-status";
        String requestQueryStr = "";
        Map req = new HashMap<>();
        req.put("card_no","822848003056012013");
        HttpUtil.post(requestPath,requestQueryStr,JSON.toJSONString(req));

    }



    @Test
    public void getBankCardBalanceTest() throws Exception {
        String requestPath = "/api/v1/bank/balance";
        String requestQueryStr = "";

        BankBalanceReq req = new BankBalanceReq();
        req.setCard_no("822848003056012013");
        HttpUtil.post(requestPath,requestQueryStr,req.toString());
    }


    @Test
    public void getBankTransactionRecordTest() throws Exception {

        String requestPath = "/api/v1/bank/transaction-record";
        String requestQueryStr = "";

        BankTxRecordsReq req = new BankTxRecordsReq();
        req.setCard_no("822848003056012013");
        req.setFormer_month_year("012020");
        req.setLatter_month_year("032020");

        HttpUtil.post(requestPath,requestQueryStr,req.toString());
    }

    @Test
    public void getBankVirtualcardTest() throws Exception {
        String requestPath = "/api/v1/bank/virtualcard";
        String requestQueryStr = "card_no=4334094500000041101";
        HttpUtil.get(requestPath,requestQueryStr);
        // example: {"code":0,"msg":"SUCCESS","result":{"encrypt_data":["fab55eed377d8d303df0f453fdd7b30e","045b8ec20f0c7c20a8eb933e5f41b30e7c6272c30a01328c03aaae3e52835d48cf466a13c17200538cb36dd49e0cbd33a159850fb8da3895fa721b7d7ab8bf83e1","04356ae3d3ddb65aeed146c61979f7456ef7ca744c95b7fea07da5d3d6b5445354245b0e2a8a56850eaac38b33cef2f7b273aeed90a68618f49b000d0378551181cc8e639cc92f24f20745ffd99f91f4"],"public_key":"031194af2b8ad8cba709509a630dfcc3746c24dfbbe9af48264df5663ad308e16f"}}

        String[] ret = new String[3];
        ret[0] = "fab55eed377d8d303df0f453fdd7b30e";
        ret[1] = "045b8ec20f0c7c20a8eb933e5f41b30e7c6272c30a01328c03aaae3e52835d48cf466a13c17200538cb36dd49e0cbd33a159850fb8da3895fa721b7d7ab8bf83e1";
        ret[2] = "04356ae3d3ddb65aeed146c61979f7456ef7ca744c95b7fea07da5d3d6b5445354245b0e2a8a56850eaac38b33cef2f7b273aeed90a68618f49b000d0378551181cc8e639cc92f24f20745ffd99f91f4";
        String privateKey = "e733f462bcd1f828820a8f6477f3ccc3b8928b00499fcf55b828bb8ed4b6ea6d";
        byte[] msg = ECIES.Decrypt(privateKey,ret);
    }

}
