package com.ruoyi.email.dto;

import com.ruoyi.common.core.domain.entity.Email;
import com.ruoyi.common.enums.shop.EmailStateEnum;

import java.util.List;

/**
 * email dto
 *
 * @author zuoyangding
 */
public class EmailOperationExecution {
    private int state;
    private String stateInfo;
    private Email email;
    private List<Email> emails;

    /**
     * for email operation failed or dao operation return type is int
     * @param emailStateEnum
     */
    public EmailOperationExecution(EmailStateEnum emailStateEnum) {
        this.state = emailStateEnum.getState();
        this.stateInfo = emailStateEnum.getStateInfo();
    }

    /**
     * for email operation for single email
     * @param emailStateEnum
     * @param email
     */
    public EmailOperationExecution(EmailStateEnum emailStateEnum, Email email) {
        this.state = emailStateEnum.getState();
        this.stateInfo = emailStateEnum.getStateInfo();
        this.email = email;
    }

    /**
     * for email operation for list of emails
     * @param emailStateEnum
     * @param emails
     */
    public EmailOperationExecution(EmailStateEnum emailStateEnum, List<Email> emails) {
        this.state = emailStateEnum.getState();
        this.stateInfo = emailStateEnum.getStateInfo();
        this.emails = emails;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }
}
