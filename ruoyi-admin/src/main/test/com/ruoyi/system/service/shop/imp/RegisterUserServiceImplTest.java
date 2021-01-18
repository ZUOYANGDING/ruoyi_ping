package com.ruoyi.system.service.shop.imp;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.shop.UserStateEnum;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.dto.UserOperationExecution;
import com.ruoyi.system.service.shop.RegisterUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class RegisterUserServiceImplTest {
    @Autowired
    RegisterUserService registerUserService;

    @Test
    public void RegisterUserByEmailAccountTest() {
        // add a shop owner
//        SysUser sysUser = new SysUser();
//        sysUser.setUserName("testOwner1");
//        sysUser.setEmail("testowner@qq.com");
//        sysUser.setPassword("123456");
//        sysUser.setNickName("testOwner1");
//        sysUser.setPhonenumber("123456789");
//        UserOperationExecution uoe = registerUserService.RegisterUserByEmailAccount(sysUser, false);
        // add a customer
        SysUser sysUser = new SysUser();
        sysUser.setUserName("testCustomer1");
        sysUser.setEmail("testCustomer@qq.com");
        sysUser.setPassword("123456");
        sysUser.setNickName("testCustomer1");
        UserOperationExecution uoe = registerUserService.RegisterUserByEmailAccount(sysUser, true);
        assertNotNull(uoe);
        assertEquals(uoe.getState(), UserStateEnum.SUCCESS.getState());
        System.out.println(uoe.getSysUser());
    }
}
