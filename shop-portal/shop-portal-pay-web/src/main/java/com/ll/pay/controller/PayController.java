package com.ll.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseResponse;
import com.ll.pay.feign.PayMentChannelServiceFeign;
import com.ll.pay.feign.PayMentContextServiceFeign;
import com.ll.pay.feign.PayMentTranscInfoServiceFeign;
import com.ll.payment.output.dto.PayMentTransacDTO;
import com.ll.payment.output.dto.PaymentChannelDTO;
import com.ll.web.base.BaseWebController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @description: 支付请求
 * @author: LL
 * @create: 2019-09-10 21:41
 */
@Controller
public class PayController extends BaseWebController {

    @Autowired
    private PayMentTranscInfoServiceFeign payMentTranscInfoServiceFeign;

    @Autowired
    private PayMentChannelServiceFeign payMentChannelServiceFeign;

    @Autowired
    private PayMentContextServiceFeign payMentContextServiceFeign;

    @RequestMapping("/")
    public String index() {
        return "index";
    }


    /**
     * @Author: LL
     * @Description: 根据Token获取待支付记录
     * @Date: 2019-09-20
     * @Param token:
     * @Param model:
     * @return: java.lang.String
     **/
    @RequestMapping("/tokenByPayMentTransc")
    public String tokenByPayMentTransc(String token, Model model) {
        if (StringUtils.isBlank(token)) {
            setErrorMsg(model, "支付令牌不能为空！");
            return ERROR_500_FTL;
        }
        BaseResponse<PayMentTransacDTO> payMentTransacDTOBaseResponse = payMentTranscInfoServiceFeign.
                tokenByPayMentTransac(token);
        if (!isSuccess(payMentTransacDTOBaseResponse)) {
            setErrorMsg(model, payMentTransacDTOBaseResponse.getMsg());
            return ERROR_500_FTL;
        }
        PayMentTransacDTO payMentTransacDTO = payMentTransacDTOBaseResponse.getData();
        List<PaymentChannelDTO> paymentChanneList = payMentChannelServiceFeign.selectAll();
        model.addAttribute("data", payMentTransacDTO);
        model.addAttribute("paymentChanneList", paymentChanneList);
        model.addAttribute("payToken", token);

        return "index";
    }

    /**
     * 解析支付页面
     *
     * @param payToken
     * @return
     * @throws IOException
     */
    @RequestMapping("/payHtml")
    public void payHtml(String channelId, String payToken, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        BaseResponse<JSONObject> payHtmlData = payMentContextServiceFeign.toPayHtml(channelId, payToken);
        if (isSuccess(payHtmlData)) {
            JSONObject data = payHtmlData.getData();
            String payHtml = data.getString("payHtml");
            response.getWriter().print(payHtml);
        }
    }
}