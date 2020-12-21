package com.ruoyi.common.enums.shop;

/**
 * coupon status enum
 *
 * @author zuoyangding
 */
public enum CouponStatusEnum {
    SUCCESS(1, "Coupon operation success"),
    INNER_ERROR(-1, "Coupon operation failed"),
    MISSING_ARGS(4, "Missing necessary params"),
    NO_COUPON(3, "Cannot find matched coupon");

    private int state;
    private String info;

    CouponStatusEnum(int state, String info) {
        this.state = state;
        this.info = info;
    }

    public static CouponStatusEnum stateOf(int state) {
        for (CouponStatusEnum statusEnum :  values()) {
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
