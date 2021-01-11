package com.ruoyi.email.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.EmailBody;
import com.ruoyi.email.dto.EmailOperationExecution;
import com.ruoyi.email.service.EmailService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.shop.CouponService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController("/index/email")
public class ImmediatelyEmailController extends BaseController {
    @Autowired
    EmailService emailService;

    @Autowired
    ISysUserService userService;

    @Autowired
    CouponService couponService;

    @PostMapping("/sendEmail")
    public AjaxResult sendEmail(@RequestBody EmailBody emailBody) {
        if (emailBody== null || emailBody.getUserEmail()==null || emailBody.getUserEmail()=="") {
            return AjaxResult.error(501, "Missing required params");
        }

        if (emailBody.getShopId()==null || emailBody.getShopId()<1
                || emailBody.getCouponId()==null || emailBody.getCouponId()<1) {
            return AjaxResult.error(500, "InnerError");
        }

        return null;
    }
}
