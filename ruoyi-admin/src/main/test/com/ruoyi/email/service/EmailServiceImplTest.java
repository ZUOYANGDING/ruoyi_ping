package com.ruoyi.email.service;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.core.domain.entity.Email;
import com.ruoyi.email.dto.EmailOperationExecution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class EmailServiceImplTest {
    @Autowired
    EmailService emailService;

    @Test
    public void getEmailsTest() {
        Email email = new Email();
        // test select all
//        EmailOperationExecution eoe = emailService.getEmails(email);
//        assertNotNull(eoe);
//        List<Email> emailList = eoe.getEmails();
//        emailList.stream().forEach(email1 -> {
//            System.out.println(email1);
//        });
        // test select by userId
        email.setUserId(100L);
//        EmailOperationExecution eoe = emailService.getEmails(email);
//        assertNotNull(eoe);
//        List<Email> emailList = eoe.getEmails();
//        emailList.stream().forEach(email1 -> {
//            System.out.println(email1);
//        });
        // test select by to_email
        email.setToEmail("testEmail@testEmail.com");
//        EmailOperationExecution eoe = emailService.getEmails(email);
//        assertNotNull(eoe);
//        List<Email> emailList = eoe.getEmails();
//        emailList.stream().forEach(email1 -> {
//            System.out.println(email1);
//        });
        // test select by send_flag
        email.setSendFlag("1");
//        EmailOperationExecution eoe = emailService.getEmails(email);
//        assertNotNull(eoe);
//        List<Email> emailList = eoe.getEmails();
//        emailList.stream().forEach(email1 -> {
//            System.out.println(email1);
//        });
        // test select by emailType
//        email.setEmailType("1");
//        EmailOperationExecution eoe = emailService.getEmails(email);
//        assertNotNull(eoe);
//        List<Email> emailList = eoe.getEmails();
//        emailList.stream().forEach(email1 -> {
//            System.out.println(email1);
//        });
        // test select by planTime
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date planTime  = null;
        try {
            planTime = sdf.parse("2020-12-31 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        email.setPlanTime(planTime);
        EmailOperationExecution eoe = emailService.getEmails(email);
        assertNotNull(eoe);
        List<Email> emailList = eoe.getEmails();
        emailList.stream().forEach(email1 -> {
            System.out.println(email1);
        });
    }

    @Test
    public void addEmailTest() {
        // test add for registered user
//        Email email = new Email();
//        email.setToEmail("test@qq.com");
//        email.setSubject("email for user ping");
//        email.setSendFlag("0");
//        email.setEmailType("0");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            email.setPlanTime(sdf.parse("2020-01-11 00:00:00"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        EmailOperationExecution eoe = emailService.addEmail(email, false);
//        assertNotNull(eoe);

        // test add for guest user
        Email email = new Email();
        email.setToEmail("testEmail_5@testEmail.com");
        email.setSubject("email for user guest");
        email.setSendFlag("1");
        email.setEmailType("1");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            email.setPlanTime(sdf.parse("2020-02-22 00:00:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        EmailOperationExecution eoe = emailService.addEmail(email, true);
        assertNotNull(eoe);
    }

    @Test
    public void updateEmailTest() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Email email = new Email();
        email.setEmailId(107L);
        email.setSendFlag("0");
        email.setEmailType("0");
        try {
            email.setPlanTime(sdf.parse("2020-01-11 00:00:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        EmailOperationExecution eoe = emailService.updateEmail(email);
        assertNotNull(eoe);
    }

    @Test
    public void deleteEmailsTest() {
        List<Email> emails = new ArrayList<>();
        Long emailId = 108L;
        for (int i=0; i<2; i++) {
            Email email = new Email();
            email.setEmailId(emailId);
            emails.add(email);
            emailId++;
        }
        EmailOperationExecution eoe = emailService.deleteEmails(emails);
        assertNotNull(eoe);
    }

    @Test
    public void deleteEmailsByEmailAddrTest() {
        String toEmail = "test@qq.com";
        EmailOperationExecution eoe = emailService.deleteEmailByEmailAddr(toEmail);
        assertNotNull(eoe);
    }
}
