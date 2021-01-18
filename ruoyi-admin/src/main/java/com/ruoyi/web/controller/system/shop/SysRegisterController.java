package com.ruoyi.web.controller.system.shop;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.enums.shop.UserStateEnum;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.system.dto.UserOperationExecution;
import com.ruoyi.system.service.shop.RegisterUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class SysRegisterController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(SysRegisterController.class);

    @Autowired
    RegisterUserService registerUserService;

    @PostMapping("/local")
    public AjaxResult registerLocalUser(@RequestBody RegisterBody registerBody) {
        if (!checkRegisterBody(registerBody)) {
            return AjaxResult.error(500, "Missing required Arguments");
        }
        SysUser currentUser;
        try {
            currentUser = parseRegisterBodyToSysUser(registerBody);

        } catch (Exception e) {
            return AjaxResult.error(501, e.getMessage());
        }

        try {
            UserOperationExecution uoe;
            if (registerBody.getIsCustomer().equals("false")) {
                uoe = registerUserService.RegisterUserByEmailAccount(currentUser, false);
            } else {
                uoe = registerUserService.RegisterUserByEmailAccount(currentUser, true);
            }
            if (uoe.getState() != UserStateEnum.SUCCESS.getState()) {
                return AjaxResult.error(501, uoe.getStateInfo());
            } else {
                return AjaxResult.success();
            }
        } catch (RuntimeException e) {
            return AjaxResult.error(501, e.getMessage());
        }
    }

    private Boolean checkRegisterBody(RegisterBody registerBody) {
        if (registerBody == null) {
            return false;
        }
        if (registerBody.getEmail()==null || registerBody.getEmail().equals("")) {
            return false;
        }
        if (registerBody.getUsername()==null || registerBody.getUsername().equals("")) {
            return false;
        }
        if (registerBody.getPassword()==null || registerBody.getPassword().equals("")) {
            return false;
        }
        if (registerBody.getNickname()==null || registerBody.getNickname().equals("")) {
            return false;
        }
        if (registerBody.getIsCustomer()==null) {
            return false;
        }
        return true;
    }

    private SysUser parseRegisterBodyToSysUser(RegisterBody registerBody) {
        SysUser user = new SysUser();
        user.setEmail(registerBody.getEmail());
        user.setUserName(registerBody.getUsername());
        user.setPassword(registerBody.getPassword());
        user.setNickName(registerBody.getNickname());
        if (registerBody.getIsCustomer().equals("false")) {
            user.setPhonenumber(registerBody.getPhoneNumber());
        }
        try {
            String userIp = IpUtils.getIpAddr(ServletUtils.getRequest());
            if (userIp != null && !userIp.equals("")) {
                user.setLoginIp(userIp);
            }
            log.debug("current user IP: {}", userIp);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        user.setCreateBy(registerBody.getUsername());
        return user;
    }
}
