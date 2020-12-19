package com.ruoyi.common.enums.shop;

public enum ShopStatesEnum {
    SUCCESS(1, "Add Shop Successfully"),
    INNER_ERROR(-1, "Add Shop Operation Failed");

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
