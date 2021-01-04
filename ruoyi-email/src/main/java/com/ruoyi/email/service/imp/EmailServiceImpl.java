package com.ruoyi.email.service.imp;

import com.ruoyi.common.core.domain.entity.Email;
import com.ruoyi.common.enums.shop.EmailStateEnum;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.email.dto.EmailOperationExecution;
import com.ruoyi.email.mapper.EmailMapper;
import com.ruoyi.email.mapper.GuestMapper;
import com.ruoyi.email.service.EmailService;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    EmailMapper emailMapper;

    @Autowired
    GuestMapper guestMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public EmailOperationExecution getEmails(Email email) {
        if (email==null) {
            return new EmailOperationExecution(EmailStateEnum.INNER_ERROR);
        }
        EmailOperationExecution eoe = null;
        try {
            List<Email> emailList = emailMapper.selectEmailList(email);
            if (emailList!=null && emailList.size()>0) {
                eoe = new EmailOperationExecution(EmailStateEnum.SUCCESS, email);
            } else {
                eoe = new EmailOperationExecution(EmailStateEnum.NO_EMAIL, email);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
        return eoe;
    }

    @Override
    public EmailOperationExecution addEmail(Email email) {
        return null;
    }

    @Override
    public EmailOperationExecution updateEmail(Email email) {
        if (email==null || email.getEmailId()==null || email.getEmailId()<1) {
            return new EmailOperationExecution(EmailStateEnum.INNER_ERROR);
        }
        try {
            int result = emailMapper.updateEmail(email);
            if (result < 0) {
                return new EmailOperationExecution(EmailStateEnum.INNER_ERROR);
            } else {
                return new EmailOperationExecution(EmailStateEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public EmailOperationExecution deleteEmail(Email email) {
        return null;
    }
}
