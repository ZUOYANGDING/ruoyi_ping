package com.ruoyi.email.mapper;

import com.ruoyi.common.core.domain.entity.Email;
import com.ruoyi.common.core.domain.entity.SysUser;

import java.util.List;

/**
 * email mapper
 *
 * @author zuoyang
 */

public interface EmailMapper {
    /**
     * selete email by id
     * @param emailId
     * @return
     */
    public Email selectEmailById(Long emailId);

    /**
     * select emails by filters, especially plan time and send time can only filter to date level
     * @param email
     * @return
     */
    public List<Email> selectEmailList(Email email);

    /**
     * insert a new email
     * @param email
     * @return
     */
    public int createEmail(Email email);

    /**
     * update email info
     * @param email
     * @return
     */
    public int updateEmail(Email email);

    /**
     * delete email by id
     * @param id
     * @return
     */
    public int deleteEmailById(Long id);

    /**
     * delete emails by ids
     * @param ids
     * @return
     */
    public int deleteEmailByIds(Long[] ids);

    /**
     * delete emails by email address
     * @param toEmail
     * @return
     */
    public int deleteEmailByEmailAddr(String toEmail);
}
