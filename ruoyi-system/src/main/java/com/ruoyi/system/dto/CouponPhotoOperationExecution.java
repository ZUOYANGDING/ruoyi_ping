package com.ruoyi.system.dto;

import com.ruoyi.common.enums.shop.CouponPhotoStatusEnum;
import com.ruoyi.system.domain.shop.CouponPhoto;

import java.util.List;

/**
 * coupon photo dto
 *
 * @author zuoyangding
 */
public class CouponPhotoOperationExecution {
    private int state;
    private String stateInfo;
    private CouponPhoto couponPhoto;
    private List<CouponPhoto> couponPhotos;

    public CouponPhotoOperationExecution() {
    }

    /**
     * for coupon photo operation failed or dao return type is int
     * @param statusEnum
     */
    public CouponPhotoOperationExecution(CouponPhotoStatusEnum statusEnum) {
        this.state = statusEnum.getState();
        this.stateInfo = statusEnum.getInfo();
    }

    /**
     * for coupon operation for single coupon
     * @param statusEnum
     * @param couponPhoto
     */
    public CouponPhotoOperationExecution(CouponPhotoStatusEnum statusEnum, CouponPhoto couponPhoto) {
        this.state = statusEnum.getState();
        this.stateInfo = statusEnum.getInfo();
        this.couponPhoto = couponPhoto;
    }

    /**
     * for coupon operation for list of coupons
     * @param statusEnum
     * @param couponPhotos
     */
    public CouponPhotoOperationExecution(CouponPhotoStatusEnum statusEnum, List<CouponPhoto> couponPhotos) {
        this.state = statusEnum.getState();
        this.stateInfo = statusEnum.getInfo();
        this.couponPhotos = couponPhotos;
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

    public CouponPhoto getCouponPhoto() {
        return couponPhoto;
    }

    public void setCouponPhoto(CouponPhoto couponPhoto) {
        this.couponPhoto = couponPhoto;
    }

    public List<CouponPhoto> getCouponPhotos() {
        return couponPhotos;
    }

    public void setCouponPhotos(List<CouponPhoto> couponPhotos) {
        this.couponPhotos = couponPhotos;
    }
}
