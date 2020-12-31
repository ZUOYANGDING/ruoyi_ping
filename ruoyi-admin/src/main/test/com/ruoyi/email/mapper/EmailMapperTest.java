package com.ruoyi.email.mapper;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.core.domain.entity.Email;
import com.ruoyi.common.core.domain.entity.Guest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class EmailMapperTest {
    @Autowired
    GuestMapper guestMapper;

    @Autowired
    EmailMapper emailMapper;

    @Test
    public void createEmailTest() {
        Guest guest = guestMapper.selectGuestById(105L);
        Email email = new Email();
        email.setUserId(guest.getGuestId());
        email.setToEmail(guest.getGuestEmail());
        email.setSubject("test email send 4");
        email.setContent("test content for for email 4 send");
        email.setAttachment("test attachment for email 4");
        // email type default 0
        email.setEmailType("0");
        // send flag default 0
        email.setSendFlag("0");
        Date now = new Date();
        email.setPlanTime(now);
        email.setSendTime(now);;

        int result = emailMapper.createEmail(email);
        assertEquals(1, result);
    }

    @Test
    public void selectEmailByIdTest() {
        Email result = emailMapper.selectEmailById(100L);
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    public void selectEmailListTest() {
        // test user Id
        Email email = new Email();
//        email.setUserId(100L);
//        System.out.println(emailMapper.selectEmailList(email));

        // test email type
//        email = new Email();
//        email.setEmailType("1");
//        System.out.println(emailMapper.selectEmailList(email));

        // test email send flag
//        email.setSendFlag("1");
//        System.out.println(emailMapper.selectEmailList(email));

        // test email plan time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date planTime = null;
        try {
            planTime = sdf.parse("2020-12-30 16:37:40");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        email.setPlanTime(planTime);
        System.out.println(emailMapper.selectEmailList(email));
    }

    @Test
    public void updateEmailTest() {
        Email email = new Email();
        // update email type
//        email.setEmailId(101L);
//        email.setEmailType("1");
//        int result = emailMapper.updateEmail(email);
//        assertEquals(result, 1);

        // update email status
        email.setEmailId(101L);
        email.setSendFlag("1");
        int result = emailMapper.updateEmail(email);
        assertEquals(result, 1);
    }

    @Test
    public void deleteEmailByIdTest() {
        Long emailId = 104L;
        int result = emailMapper.deleteEmailById(emailId);
        assertEquals(1, result);
    }

    @Test
    public void deleteEmailByIdsTest() {
        Long[] ids = {102L, 103L};
        int result = emailMapper.deleteEmailByIds(ids);
        assertEquals(2, result);
    }

    @Test
    public void deleteEmailByEmailAddrTest() {
        String toEmail = "testEmail_4@testEmail.com";
        int result = emailMapper.deleteEmailByEmailAddr(toEmail);
        assertEquals(2, result);
    }
}
