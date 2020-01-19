package com.noumena.open.api.dto;

import com.alibaba.fastjson.JSONObject;

public class NewCardReq {

    //要充值的用户编号
    private String acct_no;

    //银行ID
    private String bank_id;

//   机构端交易流水号
    private String cust_tx_id;


    public String getAcct_no() {
        return acct_no;
    }
    public void setAcct_no(String acct_no) {
        this.acct_no = acct_no;
    }

    public String getBank_id() {
        return bank_id;
    }
    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getCust_tx_id() {
        return cust_tx_id;
    }


    public void setCust_tx_id(String cust_tx_id) {
        this.cust_tx_id = cust_tx_id;
    }

    @Override
    public String toString(){
        return JSONObject.toJSONString(this);
    }
}
