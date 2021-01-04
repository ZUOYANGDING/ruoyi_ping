package com.ruoyi.email.dto;

import com.ruoyi.common.core.domain.entity.Guest;
import com.ruoyi.common.enums.shop.GuestStatusEnum;

/**
 * guest dto
 *
 * @author zuoyangding
 */
public class GuestOperationExecution {
    private int state;
    private String stateInfo;
    private Guest guest;

    /**
     * for guest operation failed or dao operation return type is int
     * @param guestStatusEnum
     */
    public GuestOperationExecution(GuestStatusEnum guestStatusEnum) {
        this.state = guestStatusEnum.getState();
        this.stateInfo = guestStatusEnum.getStateInfo();
    }

    /**
     * for email operation for single guest
     * @param guestStatusEnum
     * @param guest
     */
    public GuestOperationExecution(GuestStatusEnum guestStatusEnum, Guest guest) {
        this.state = guestStatusEnum.getState();
        this.stateInfo = guestStatusEnum.getStateInfo();
        this.guest = guest;
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

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }
}
