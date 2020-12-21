package com.ruoyi.system.dto;

import com.ruoyi.common.enums.shop.ShopPhotoStatusEnum;
import com.ruoyi.system.domain.shop.ShopPhoto;

import java.util.List;

/**
 * shop photo dto
 *
 * @author zuoyangding
 */
public class ShopPhotoOperationExecution {
    private int state;
    private String stateInfo;
    private ShopPhoto shopPhoto;
    private List<ShopPhoto> shopPhotos;

    public ShopPhotoOperationExecution(){}

    /**
     * for shop operation failed or dao operation return type is int
     * @param statusEnum
     */
    public ShopPhotoOperationExecution(ShopPhotoStatusEnum statusEnum) {
        this.state = statusEnum.getState();
        this.stateInfo = statusEnum.getInfo();
    }

    /**
     * for shop operation for single shop
     * @param statusEnum
     * @param shopPhoto
     */
    public ShopPhotoOperationExecution(ShopPhotoStatusEnum statusEnum, ShopPhoto shopPhoto) {
        this.state = statusEnum.getState();
        this.stateInfo = statusEnum.getInfo();
        this.shopPhoto = shopPhoto;
    }

    /**
     * for shop opertaion for list of shops
     * @param statusEnum
     * @param shopPhotos
     */
    public ShopPhotoOperationExecution(ShopPhotoStatusEnum statusEnum, List<ShopPhoto> shopPhotos) {
        this.state = statusEnum.getState();
        this.stateInfo = statusEnum.getInfo();
        this.shopPhotos = shopPhotos;
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

    public ShopPhoto getShopPhoto() {
        return shopPhoto;
    }

    public void setShopPhoto(ShopPhoto shopPhoto) {
        this.shopPhoto = shopPhoto;
    }

    public List<ShopPhoto> getShopPhotos() {
        return shopPhotos;
    }

    public void setShopPhotos(List<ShopPhoto> shopPhotos) {
        this.shopPhotos = shopPhotos;
    }
}
