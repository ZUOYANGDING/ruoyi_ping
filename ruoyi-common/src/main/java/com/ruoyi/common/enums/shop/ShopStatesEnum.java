package com.ruoyi.common.enums.shop;

/**
 * shop status enum
 *
 * @author zuoyangding
 */
public enum ShopStatesEnum {
    SUCCESS(1, "Shop Operation Success"),
    SHOP_UNDER_CHECK(2, "Shop is under checking"),
    NO_SHOP(3, "Cannot find matched shop"),
    MISSING_ARGS(4, "Missing necessary params"),
    INNER_ERROR(-1, "Shop Operation Failed");

    private int state;
    private String info;

    ShopStatesEnum(int state, String info) {
        this.state = state;
        this.info = info;
    }

    public static ShopStatesEnum stateOf(int state) {
        for (ShopStatesEnum statesEnum : values()) {
            if (statesEnum.getState() == state) {
                return statesEnum;
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
