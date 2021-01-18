package com.ruoyi.system.service.shop.imp;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.shop.UserStateEnum;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.dto.UserOperationExecution;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.shop.RegisterUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * user register service impl
 *
 * @author zuoyangding
 */
@Service
public class RegisterUserServiceImpl implements RegisterUserService {

    private static final Logger log = LoggerFactory.getLogger(RegisterUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;


    @Override
    @Transactional
    public UserOperationExecution RegisterUserByEmailAccount(SysUser user, boolean isCustomer) {
        // check necessary param for user
        if(!checkInitUser(user)) {
            return new UserOperationExecution(UserStateEnum.MISSING_ARGS);
        }

        // check repeat user email and user name
        try {
            if (checkRepeatUserEmail(user)){
                return new UserOperationExecution(UserStateEnum.REPEAT_EMAIL);
            }
            if (checkRepeatUserName(user)) {
                return new UserOperationExecution(UserStateEnum.REPEAT_NAME);
            }
        } catch (RuntimeException e) {
            throw new CustomException("check user param repeat error in db operation");
        }


        // set department ping to new user
        user.setDeptId(110L);

        // set up user status
        user.setStatus("0");
        user.setDelFlag("0");

        // check phone number if owner
        if (!isCustomer) {
            if (user.getPhonenumber()==null || user.getPhonenumber().equals("")) {
                return new UserOperationExecution(UserStateEnum.MISSING_ARGS);
            }
        }

        // set up password
        String password = SecurityUtils.encryptPassword(user.getPassword());
        user.setPassword(password);

        // store user
        try {
            int result = userMapper.insertUser(user);
            if (result < 1) {
                return new UserOperationExecution(UserStateEnum.INNER_ERROR);
            } else {
                // link in user_role form
                try {
                    if (isCustomer) {
                        linkWithCustomer(user);
                    } else {
                        linkWithShopOwner(user);
                    }
                } catch (RuntimeException e) {
                    throw new CustomException("Link user to role error " + e.getMessage());
                }
                return new UserOperationExecution(UserStateEnum.SUCCESS, user);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }

    }

    @Override
    public UserOperationExecution RegisterUserByGmail() {
        return null;
    }

    @Override
    public UserOperationExecution RegisterUserByFacebook() {
        return null;
    }

    /**
     * check necessary param
     * @param user
     * @return
     */
    private boolean checkInitUser(SysUser user) {
        if (user == null) {
            return false;
        }
        if (user.getEmail()==null || user.getEmail().equals("")) {
            return false;
        }
        if (user.getUserName()==null || user.getUserName().equals("")) {
            return false;
        }
        if (user.getNickName()==null || user.getNickName().equals("")) {
            return false;
        }
        if (user.getPassword()==null || user.getPassword().equals("")) {
            return false;
        }
        return true;
    }

    /**
     * check if repeat email
     * @param user
     * @return
     * @throws RuntimeException
     */
    private boolean checkRepeatUserEmail(SysUser user) throws RuntimeException{
        try {
            int result = userMapper.checkUserNameUnique(user.getUserName());
            if (result < 1) {
                return false;
            } else {
                return true;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * check if repeat user name
     * @param user
     * @return
     * @throws RuntimeException
     */
    private boolean checkRepeatUserName(SysUser user) throws RuntimeException {
        try {
            int result = userMapper.checkUserNameUnique(user.getUserName());
            if (result < 1) {
                return false;
            } else {
                return true;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * record link in user_role form for customer
     * @param user
     * @throws RuntimeException
     */
    @Transactional
    public void linkWithCustomer(SysUser user) throws RuntimeException{
        try {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(user.getUserId());
            sysUserRole.setRoleId(4L);
            List<SysUserRole> sysUserRoleList = new ArrayList<>();
            sysUserRoleList.add(sysUserRole);
            int result = userRoleMapper.batchUserRole(sysUserRoleList);
            if (result < 1) {
                throw new CustomException("Did not create link in user_role form for customer");
            }
        } catch (RuntimeException e) {
            throw new CustomException("Did not create link in user_role form form for customer " + e.getMessage());
        }
    }

    /**
     * record link in user_role form for shop owner
     * @param user
     * @throws RuntimeException
     */
    @Transactional
    public void linkWithShopOwner(SysUser user) throws RuntimeException{
        try {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(user.getUserId());
            sysUserRole.setRoleId(3L);
            List<SysUserRole> sysUserRoleList = new ArrayList<>();
            sysUserRoleList.add(sysUserRole);
            int result = userRoleMapper.batchUserRole(sysUserRoleList);
            if (result < 1) {
                throw new CustomException("Did not create link in user_role form for owner");
            }
        } catch (RuntimeException e) {
            throw new CustomException("Did not create link in user_role form for owner " + e.getMessage());
        }
    }
}
