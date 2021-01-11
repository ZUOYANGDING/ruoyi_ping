package com.ruoyi.email.service;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.core.domain.model.EmailVo;
import com.ruoyi.common.utils.file.shop.ShopImgFileUtil;
import com.ruoyi.system.domain.shop.CouponPhoto;
import com.ruoyi.system.dto.CouponOperationExecution;
import com.ruoyi.system.dto.CouponPhotoOperationExecution;
import com.ruoyi.system.service.shop.CouponPhotoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class EmailSendServiceImplTest {
    @Autowired
    EmailSendService emailSendService;

    @Autowired
    CouponPhotoService couponPhotoService;

    @Test
    public void emailSendTest() {
        EmailVo emailVo = new EmailVo();
        emailVo.setEmailId("test 1");
        emailVo.setToEmail("zuoyangdingfade@163.com");
        emailVo.setContent("This is a test email");
        emailVo.setSubject("Test email one");
        emailVo.setSendDate(new Date());

        CouponPhotoOperationExecution coe = couponPhotoService.selectCouponPhotoById(133L);
        assertNotNull(coe.getCouponPhoto().getPhoto());
        ArrayList<MultipartFile> attachment = new ArrayList<>();
        try {
            attachment.add(ShopImgFileUtil.getEmailAttachment(coe.getCouponPhoto().getPhoto()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        emailVo.setAttachment(attachment);
        EmailVo email = emailSendService.sendEmail(emailVo);
        System.out.println(email);
    }
}
