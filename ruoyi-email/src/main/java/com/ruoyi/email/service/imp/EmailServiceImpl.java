package com.ruoyi.email.service.imp;

import com.ruoyi.common.core.domain.entity.Email;
import com.ruoyi.common.core.domain.entity.Guest;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.shop.EmailStateEnum;
import com.ruoyi.common.enums.shop.GuestStatusEnum;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.email.dto.EmailOperationExecution;
import com.ruoyi.email.dto.GuestOperationExecution;
import com.ruoyi.email.mapper.EmailMapper;
import com.ruoyi.email.mapper.GuestMapper;
import com.ruoyi.email.service.EmailService;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
                eoe = new EmailOperationExecution(EmailStateEnum.SUCCESS, emailList);
            } else {
                eoe = new EmailOperationExecution(EmailStateEnum.NO_EMAIL);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
        return eoe;
    }

    @Override
    @Transactional
    public EmailOperationExecution addEmail(Email email, boolean isGuest) {
        if (email==null || email.getToEmail()==null || email.getToEmail().equals("")) {
            return new EmailOperationExecution(EmailStateEnum.INNER_ERROR);
        }
        if (isGuest) {
            try {
                // store guest into db and get userID
                Long userId = null;
                Guest guest = new Guest();
                guest.setGuestEmail(email.getToEmail());
                int guest_result = -1;
                guest_result = guestMapper.insertGuest(guest);
                if (guest_result <= 0) {
                    return new EmailOperationExecution(EmailStateEnum.INNER_ERROR);
                } else {
                    userId = guestMapper.selectGuestByEmail(email.getToEmail()).getGuestId();
                    if (userId < 1) {
                        return new EmailOperationExecution(EmailStateEnum.INNER_ERROR);
                    }
                }

                // store email into db
                email.setUserId(userId);
                int email_result = emailMapper.createEmail(email);
                if (email_result <= 0) {
                    return new EmailOperationExecution(EmailStateEnum.INNER_ERROR);
                } else {
                    return new EmailOperationExecution(EmailStateEnum.SUCCESS);
                }
            } catch (RuntimeException e) {
                throw new CustomException(e.getMessage());
            }
        } else {
            try {
                // get userId from db
                Long userId = null;
                try {
                   SysUser sysUser = sysUserMapper.selectUserByEmail(email.getToEmail());
                   if (sysUser==null || sysUser.getUserId()==null || sysUser.getUserId()<1) {
                       return new EmailOperationExecution(EmailStateEnum.INNER_ERROR);
                   } else {
                       userId = sysUser.getUserId();
                   }
                } catch (RuntimeException e) {
                    throw new CustomException(e.getMessage());
                }

                // store email
                email.setUserId(userId);
                int email_result = emailMapper.createEmail(email);
                if (email_result <= 0) {
                    return new EmailOperationExecution(EmailStateEnum.INNER_ERROR);
                } else {
                    return new EmailOperationExecution(EmailStateEnum.SUCCESS);
                }
            } catch (RuntimeException e) {
                throw new CustomException(e.getMessage());
            }
        }

    }

    @Override
    @Transactional
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
    @Transactional
    public EmailOperationExecution deleteEmail(Email email) {
        if (email==null || email.getEmailId()==null || email.getEmailId()<1) {
            return new EmailOperationExecution(EmailStateEnum.INNER_ERROR);
        }

        try {
            int result = emailMapper.deleteEmailById(email.getEmailId());
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
    @Transactional
    public EmailOperationExecution deleteEmails(List<Email> emails) {
        if (emails==null || emails.size()<1) {
            return new EmailOperationExecution(EmailStateEnum.INNER_ERROR);
        }
        List<Long> emailIds = new ArrayList<>();
        for (Email email : emails) {
            if (email.getEmailId()==null ||  email.getEmailId()<1) {
                return new EmailOperationExecution(EmailStateEnum.INNER_ERROR);
            } else {
                emailIds.add(email.getEmailId());
            }
        }
        Long[] ids = new Long[emailIds.size()];
        int index = 0;
        for (Long emailId : emailIds) {
            ids[index] = emailId;
            index++;
        }
        try {
            int result = emailMapper.deleteEmailByIds(ids);
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
    @Transactional
    public EmailOperationExecution deleteEmailByEmailAddr(String toEmail) {
        if (toEmail==null || toEmail.equals("")){
            return new EmailOperationExecution(EmailStateEnum.INNER_ERROR);
        }

        try {
            int result = emailMapper.deleteEmailByEmailAddr(toEmail);
            if (result < 0) {
                return new EmailOperationExecution(EmailStateEnum.INNER_ERROR);
            } else {
                return new EmailOperationExecution(EmailStateEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }
}
