package com.ruoyi.common.enums.shop;

/**
 * coupon status enum
 *
 * @author zuoyangding
 */
public enum CouponPhotoStatusEnum {
    SUCCESS(1, "Coupon Photo Operation Success"),
    NO_PHOTO(3, "Cannot find matched photo"),
    MISSING_ARGS(4, "Missing necessary params"),
    INNER_ERROR(-1, "Coupon Photo Operation Failed");

    private int state;
    private String info;

    CouponPhotoStatusEnum(int state, String info) {
        this.state = state;
        this.info = info;
    }

    public CouponPhotoStatusEnum stateOf(int state) {
        for (CouponPhotoStatusEnum statusEnum : values()) {
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
