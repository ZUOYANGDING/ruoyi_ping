package com.ruoyi.email.service;

import com.ruoyi.common.core.domain.model.EmailVo;

public interface EmailSendService {
    public EmailVo sendEmail(EmailVo emailVo);
}
