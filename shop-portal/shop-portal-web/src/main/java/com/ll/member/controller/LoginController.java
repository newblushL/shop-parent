package com.ll.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseResponse;
import com.ll.constants.Constants;
import com.ll.member.controller.req.vo.LoginVO;
import com.ll.member.feign.MemberLoginServiceFeign;
import com.ll.member.input.dto.UserLoginInpDTO;
import com.ll.web.base.BaseWebController;
import com.ll.web.bean.PropertyUtils;
import com.ll.web.constants.WebConstants;
import com.ll.web.utils.CookieUtils;
import com.ll.web.utils.RandomValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @description: 登陆请求
 * @author: LL
 * @create: 2019-08-27 16:24
 */
@Controller
public class LoginController extends BaseWebController {
    /**
     * 登陆页面
     */
    private static final String MB_LOGIN_FTL = "member/login";
    /**
     * 重定向到首页
     */
    private static final String REDIRECT_INDEX = "redirect:/";

    @Autowired
    private MemberLoginServiceFeign memberLoginServiceFeign;

    /**
     * 跳转到登陆页面
     *
     * @return
     */
    @GetMapping("/login")
    public String getLogin() {
        return MB_LOGIN_FTL;
    }

    /**
     * 用户注册
     *
     * @return
     */
    @PostMapping("/login")
    public String postLogin(@ModelAttribute("loginVO") @Validated LoginVO loginVO,
                            BindingResult bindingResult, Model model, HttpServletRequest request,
                            HttpServletResponse response, HttpSession session) {
        // 验证参数
        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldError().getDefaultMessage();
            setErrorMsg(model, errMsg);
            return MB_LOGIN_FTL;
        }
        // 验证图形验证码
        String graphicCode = loginVO.getGraphicCode();
        boolean checkVerify = RandomValidateCodeUtil.checkVerify(graphicCode, session);
        if (!checkVerify) {
            setErrorMsg(model, "图形验证码不正确");
            return MB_LOGIN_FTL;
        }
        // 调用Feign接口
        UserLoginInpDTO userLoginInpDTO = PropertyUtils.voToDto(loginVO, UserLoginInpDTO.class);
        userLoginInpDTO.setLoginType(Constants.MEMBER_LOGIN_TYPE_PC);
        userLoginInpDTO.setDeviceInfor(webBrowserInfo(request));
        BaseResponse<JSONObject> login = memberLoginServiceFeign.login(userLoginInpDTO);
        if (!isSuccess(login)) {
            setErrorMsg(model, login.getMsg());
            return MB_LOGIN_FTL;
        }
        // 登陆成功之后如何处理 保持会话信息 将token存入到cookie 里面 首页读取cookietoken
        // 查询用户信息返回到页面展示
        JSONObject data = login.getData();
        String token = data.getString("token");
        CookieUtils.setCookie(request, response, WebConstants.LOGIN_TOKEN_COOKIENAME, token);
        return REDIRECT_INDEX;
    }
}
