package com.ruoyi.email.service.imp;

import com.ruoyi.common.core.domain.model.EmailVo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.email.service.EmailSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service

public class EmailSendServiceImpl implements EmailSendService {
    private final Logger log = LoggerFactory.getLogger(EmailSendServiceImpl.class);

    @Autowired
    private JavaMailSenderImpl mailSender;


    @Override
    public EmailVo sendEmail(EmailVo emailVo) {
        try {
            checkMail(emailVo);
            sendMimeMail(emailVo);
            return emailVo;
        } catch (RuntimeException e) {
            log.debug("send email failed");
            emailVo.setStatus("fail");
            throw new RuntimeException(e.getMessage());
        }

    }

    private void checkMail(EmailVo emailVo) {
        if (StringUtils.isEmpty(emailVo.getToEmail())) {
            throw new RuntimeException("email receiver is null");
        }
        if (StringUtils.isEmpty(emailVo.getSubject())) {
            throw new RuntimeException("email's subject is null");
        }
        if (StringUtils.isEmpty(emailVo.getContent())) {
            throw new RuntimeException("email's content is null");
        }
    }

    private void sendMimeMail(EmailVo emailVo) {
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            emailVo.setFromEmail(getEmailFrom());
            log.debug("email from {}", emailVo.getFromEmail());
            messageHelper.setFrom(emailVo.getFromEmail());
            messageHelper.setTo(emailVo.getToEmail().split(","));
            messageHelper.setSubject(emailVo.getSubject());
            messageHelper.setSentDate(emailVo.getSendDate());
            messageHelper.setText(emailVo.getContent());

            if (emailVo.getAttachment() != null) {
                for (MultipartFile multipartFile : emailVo.getAttachment()) {
                    messageHelper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
                }
            }

            mailSender.send(messageHelper.getMimeMessage());
            emailVo.setStatus("ok");
            log.debug("=======send message successfully======");
        } catch (Exception e) {
            log.debug("message send error {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private String getEmailFrom() {
        return mailSender.getJavaMailProperties().getProperty("from");
    }
}
