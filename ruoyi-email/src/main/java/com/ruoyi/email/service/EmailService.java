package com.ruoyi.email.service;

import com.ruoyi.common.core.domain.entity.Email;
import com.ruoyi.email.dto.EmailOperationExecution;

import java.util.List;

public interface EmailService {
    public EmailOperationExecution getEmails(Email email);

    public EmailOperationExecution addEmail(Email email);

    public EmailOperationExecution updateEmail(Email email);

    public EmailOperationExecution deleteEmail(Email email);
}
