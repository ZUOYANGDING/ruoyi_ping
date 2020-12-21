package com.ruoyi.common.enums.shop;

/**
 * shop photo enum
 *
 * @author zuoyangding
 */
public enum ShopPhotoStatusEnum {
    SUCCESS(1, "Shop Photo Operation Success"),
    NO_PHOTO(3, "Cannot find matched photo"),
    MISSING_ARGS(4, "Missing necessary params"),
    INNER_ERROR(-1, "Shop Photo Operation Failed");


    private int state;
    private String info;

    ShopPhotoStatusEnum(int state, String info) {
        this.state = state;
        this.info = info;
    }

    public static ShopPhotoStatusEnum stateOf(int state) {
        for (ShopPhotoStatusEnum statesEnum : values()) {
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
