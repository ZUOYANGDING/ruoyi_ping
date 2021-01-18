package com.ruoyi.system.dto;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.shop.UserStateEnum;

public class UserOperationExecution {
    private int state;
    private String stateInfo;
    private SysUser sysUser;

    public UserOperationExecution() {}

    public UserOperationExecution(UserStateEnum userStateEnum) {
        this.state = userStateEnum.getState();
        this.stateInfo = userStateEnum.getStateInfo();
    }

    public UserOperationExecution(UserStateEnum userStateEnum, SysUser user) {
        this.state = userStateEnum.getState();
        this.stateInfo = userStateEnum.getStateInfo();
        this.sysUser = user;
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

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}
