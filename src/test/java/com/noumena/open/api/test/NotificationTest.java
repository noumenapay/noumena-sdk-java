package com.noumena.open.api.test;

import com.noumena.open.api.util.HmacUtil;
import org.junit.Test;

/**
 * @author Sheldon Zhao
 * @date 3/27/20
 */
public class NotificationTest {
    @Test
    public void notificationTest() {
        //what you receive
        String timeStamp = "1585310160226";
        String signature = "UqAwtsx9HF3s5yJh/c8luvUITZNXE/f3aujwndnXLBU=";
        //action and your secret
        String action = "card-application";
        String secret = "ba3a3c0c38dc434eb25225ab06135743";
        //verify
        try {
            String sign = HmacUtil.sign(timeStamp, action, secret);
            System.out.println(sign);
            if(sign.equals(signature)){
                System.out.println("signature is right");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
