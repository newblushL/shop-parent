package com.ll.zuul;

import com.ll.core.utils.HttpClientUtils;
import com.ll.core.utils.SignUtil;

import java.util.HashMap;
import java.util.Map;

public class TestUtils {

    public static void main(String[] args) {
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("payAmount", "300222");
        sParaTemp.put("orderId", "2019091503501502");
        sParaTemp.put("userId", "644064");
        String reuslt = HttpClientUtils.doPost("http://127.0.0.1/api-pay/cratePayToken", SignUtil.sign(sParaTemp));
        System.out.println("reusltL:" + reuslt);
    }
}
