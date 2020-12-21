package com.ruoyi.system.dto;

import com.ruoyi.common.enums.shop.CouponStatusEnum;
import com.ruoyi.system.domain.shop.Coupon;

import java.util.List;

/**
 * coupon dto
 *
 * @author zuoyangding
 */
public class CouponOperationExecution {
    private int state;
    private String stateInfo;
    private Coupon coupon;
    private List<Coupon> couponList;

    public CouponOperationExecution() {
    }

    /**
     * for coupon operation failed or dao return type is int
     * @param statusEnum
     */
    public CouponOperationExecution(CouponStatusEnum statusEnum) {
        this.state = statusEnum.getState();
        this.stateInfo = statusEnum.getInfo();
    }

    /**
     * for coupon operation for single coupon
     * @param statusEnum
     * @param coupon
     */
    public CouponOperationExecution(CouponStatusEnum statusEnum, Coupon coupon) {
        this.state = statusEnum.getState();
        this.stateInfo = statusEnum.getInfo();
        this.coupon = coupon;
    }

    /**
     * for coupon operation for list of coupons
     * @param statusEnum
     * @param coupons
     */
    public CouponOperationExecution(CouponStatusEnum statusEnum, List<Coupon> coupons) {
        this.state = statusEnum.getState();
        this.stateInfo = statusEnum.getInfo();
        this.couponList = coupons;
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

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }
}
