package com.ruoyi.system.service.shop;

import com.ruoyi.system.domain.shop.Coupon;
import com.ruoyi.system.dto.CouponOperationExecution;

import java.math.BigDecimal;
import java.util.List;

/**
 * coupon service interface
 *
 * @author zuoyangding
 */
public interface CouponService {
    public CouponOperationExecution getCouponByCouponId(Long couponId);

    public CouponOperationExecution getCouponList(Coupon coupon);

    public CouponOperationExecution addCoupon(Coupon coupon);

    public CouponOperationExecution getCouponListByPrice(List<BigDecimal> priceInterval);

    public CouponOperationExecution updateCouponProfile(Coupon coupon);

    public CouponOperationExecution deleteCouponByCouponId(Long couponId);

    public CouponOperationExecution deleteCouponByCouponIds(Long[] couponIds);
}
