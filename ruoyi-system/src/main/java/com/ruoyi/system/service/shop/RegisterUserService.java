package com.ruoyi.system.service.shop;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.dto.UserOperationExecution;

public interface RegisterUserService {
    /**
     * register user for both customer and shop owner
     * @param user
     * @param isCustomer
     * @return
     */
    public UserOperationExecution RegisterUserByEmailAccount(SysUser user, boolean isCustomer);

    public UserOperationExecution RegisterUserByGmail();

    public UserOperationExecution RegisterUserByFacebook();
}
