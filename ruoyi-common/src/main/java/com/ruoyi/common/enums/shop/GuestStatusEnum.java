package com.ruoyi.common.enums.shop;

/**
 * guest status enum
 *
 * @author zuoyangding
 */

public enum GuestStatusEnum {
    SUCCESS(1, "Guest Operation Success"),
    NO_PHOTO(3, "Cannot find matched guest"),
    MISSING_ARGS(4, "Missing necessary params"),
    INNER_ERROR(-1, "Guest Operation Failed");

    private int state;
    private String stateInfo;

    GuestStatusEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public GuestStatusEnum stateOf(int state) {
        for (GuestStatusEnum statusEnum : values()) {
            if (statusEnum.getState() == state) {
                return statusEnum;
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
