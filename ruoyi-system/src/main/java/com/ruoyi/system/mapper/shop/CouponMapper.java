package com.ruoyi.system.mapper.shop;

import com.ruoyi.system.domain.shop.Coupon;

public interface CouponMapper {
    public Coupon selectCouponByCouponId(Long couponId);

    public int insertCoupon(Coupon coupon);
}
