package com.ll.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseResponse;
import com.ll.member.controller.req.vo.RegisterVO;
import com.ll.member.feign.MemberRegisterServiceFeign;
import com.ll.member.input.dto.UserInpDTO;
import com.ll.web.base.BaseWebController;
import com.ll.web.bean.PropertyUtils;
import com.ll.web.utils.RandomValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @description: 注册请求
 * @author: LL
 * @create: 2019-08-27 16:24
 */
@Controller
public class RegisterController extends BaseWebController {
    /**
     * 注册页面
     */
    private static final String MB_REGISTER_FTL = "member/register";

    /**
     * 登陆页面
     */
    private static final String MB_LOGIN_FTL = "member/login";

    @Autowired
    private MemberRegisterServiceFeign memberRegisterServiceFeign;

    /**
     * 跳转到注册页面
     *
     * @return
     */
    @GetMapping("/register")
    public String getRegister() {
        return MB_REGISTER_FTL;
    }

    /**
     * 用户注册
     *
     * @return
     */
    @PostMapping("/register")
    public String postRegister(@ModelAttribute("registerVO") @Validated RegisterVO registerVO,
                               Model model, BindingResult bindingResult, HttpSession
                                       httpSession) {
        // 验证参数
        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldError().getDefaultMessage();
            setErrorMsg(model, errMsg);
            return MB_REGISTER_FTL;
        }
        // 验证图形验证码
        String graphicCode = registerVO.getGraphicCode();
        boolean checkVerify = RandomValidateCodeUtil.checkVerify(graphicCode, httpSession);
        if (!checkVerify) {
            setErrorMsg(model, "图形验证码不正确");
            return MB_REGISTER_FTL;
        }
        // 调用Feign接口
        UserInpDTO userInpDTO = PropertyUtils.voToDto(registerVO, UserInpDTO.class);
        BaseResponse<JSONObject> register = memberRegisterServiceFeign.register(userInpDTO,
                registerVO.getRegistCode());
        if (isSuccess(register)) {
            setErrorMsg(model, register.getMsg());
            return MB_REGISTER_FTL;
        }
        // 返回登陆页面
        return MB_LOGIN_FTL;
    }

}