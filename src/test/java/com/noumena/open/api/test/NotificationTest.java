package com.noumena.open.api.test;

import com.alibaba.fastjson.JSONObject;
import com.noumena.open.api.util.HmacUtil;
import org.junit.Test;

import java.util.TreeMap;

/**
 * @author Sheldon Zhao
 * @date 3/27/20
 */
public class NotificationTest {
    @Test
    public void notificationTest() {
        //what you receive in header
        String timeStamp = "1585556940084";
        String signature = "IovphWdYmsGSMislwxzlgGkjoSDqQEDugjeKl/9JecU=";
        //action, params and your secret
        String action = "kyc-status";
        String secret = "ba3a3c0c38dc434eb25225ab06135743";
        String params = "{\"acct_no\":\"acct009\",\"card_type_id\":\"70000001\",\"status\":1}";
        TreeMap<String, Object> treeMap = JSONObject.parseObject(params, TreeMap.class);

        //verify
        try {
            String sign = HmacUtil.sign(timeStamp, action, treeMap, secret);
            System.out.println(sign);
            if (sign.equals(signature)) {
                System.out.println("signature is right");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
