package com.ruoyi.system.dto;

import com.ruoyi.common.enums.shop.ShopStatesEnum;
import com.ruoyi.system.domain.shop.Shop;

import java.util.List;

/**
 * shop dto
 *
 * @author zuoyangding
 */
public class ShopOperationExecution {
    private int state;
    private String stateInfo;
    private Shop shop;
    private List<Shop> shopList;

    public ShopOperationExecution(){}


    /**
     * for shop operation failed or dao operation return type is int
     * @param shopStatesEnum
     */
    public ShopOperationExecution(ShopStatesEnum shopStatesEnum) {
        this.state = shopStatesEnum.getState();
        this.stateInfo = shopStatesEnum.getInfo();
    }

    /**
     * for shop operation for single shop
     * @param shopStatesEnum
     * @param shop
     */
    public ShopOperationExecution(ShopStatesEnum shopStatesEnum, Shop shop) {
        this.state = shopStatesEnum.getState();
        this.stateInfo = shopStatesEnum.getInfo();
        this.shop = shop;
    }

    /**
     * for shop operation for list of shops
     * @param shopStatesEnum
     * @param shopList
     */
    public ShopOperationExecution(ShopStatesEnum shopStatesEnum, List<Shop> shopList) {
        this.state = shopStatesEnum.getState();
        this.stateInfo = shopStatesEnum.getInfo();
        this.shopList = shopList;
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
