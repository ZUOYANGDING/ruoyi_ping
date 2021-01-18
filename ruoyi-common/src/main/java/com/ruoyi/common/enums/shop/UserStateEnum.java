package com.ruoyi.common.enums.shop;


/**
 * User status enum
 *
 * @author zuoyangding
 */
public enum UserStateEnum {
    SUCCESS(1, "User Operation Success"),
    NO_EMAIL(3, "Cannot find matched User"),
    MISSING_ARGS(4, "Missing necessary params"),
    REPEAT_EMAIL(5, "Email has already registered"),
    REPEAT_NAME(6, "Username has already been used"),
    INNER_ERROR(-1, "User Operation Failed");

    int state;
    String stateInfo;

    UserStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public UserStateEnum stateOf(int state) {
        for (UserStateEnum stateEnum : values()) {
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
