package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * email info
 *
 * @author zuoyang
 */

public class Email {
    private static final long serialVersionUID = -1148587605568083975L;

    /**
     * email id
     */
    @Excel(name="Email ID", cellType = Excel.ColumnType.NUMERIC, prompt = "Email Id")
    private Long emailId;

    /**
     * acceptor user id
     */
    @Excel(name="User Id", cellType = Excel.ColumnType.NUMERIC)
    private Long userId;

    /**
     * acceptor email
     */
    @Excel(name="Acceptor email address", cellType = Excel.ColumnType.STRING)
    private String toEmail;

    /**
     * email subject
     */
    @Excel(name="Email subject", cellType = Excel.ColumnType.STRING)
    private String subject;

    /**
     * email content
     */
    @Excel(name="Email content", cellType = Excel.ColumnType.STRING)
    private String content;

    /**
     * email attachment
     */
    @Excel(name="Email attachment", cellType = Excel.ColumnType.STRING)
    private String attachment;

    /**
     * email sent or not
     */
    @Excel(name="Sent Or not", cellType = Excel.ColumnType.STRING, readConverterExp = "0=sent, 1=not yet")
    private String sendFlag;

    /**
     * type of email
     * 0 for send immediately
     * 1 for send in plan
     */
    @Excel(name="Type of email", cellType = Excel.ColumnType.STRING, readConverterExp = "0=send immediately, 1=send by plan time")
    private String emailType;

    /**
     * time for send plan
     */
    @Excel(name="Plan Time", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date planTime;

    /**
     * email sent time
     */
    @Excel(name="send Time", width = 30, dateFormat = "yyy-MM-dd HH:mm:ss")
    private Date sendTime;

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Email Id", getEmailId())
                .append("user Id", getUserId())
                .append("To email", getToEmail())
                .append("Subject", getSubject())
                .append("content", getContent())
                .append("attachment", getAttachment())
                .append("Send Flag", getSendFlag())
                .append("email Type", getEmailType())
                .append("Plan Time", getPlanTime())
                .append("Send Time", getSendTime())
                .toString();
    }
}
