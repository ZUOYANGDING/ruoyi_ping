package com.ruoyi.common.enums.shop;

/**
 * email status enum
 *
 * @author zuoyangding
 */

public enum EmailStateEnum {
    SUCCESS(1, "Email Operation Success"),
    NO_EMAIL(3, "Cannot find matched email"),
    MISSING_ARGS(4, "Missing necessary params"),
    INNER_ERROR(-1, "Email Operation Failed");

    private int state;
    private String stateInfo;

    EmailStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public EmailStateEnum stateOf(int state) {
        for (EmailStateEnum stateEnum : values()) {
            if (stateEnum.getState() == state) {
                return stateEnum;
            }
        }
        return null;
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
}
