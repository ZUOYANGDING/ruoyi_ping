package com.ruoyi.system.service.shop;

import com.ruoyi.system.domain.shop.Coupon;
import com.ruoyi.system.dto.CouponOperationExecution;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * coupon service interface
 *
 * @author zuoyangding
 */
public interface CouponService {
    /**
     * select single coupon by coupon id
     * @param couponId
     * @return
     */
    public CouponOperationExecution getCouponByCouponId(Long couponId);

    /**
     * fetch list of coupons
     * @param coupon
     * @return
     */
    public CouponOperationExecution getCouponList(Coupon coupon);

    /**
     * create new coupon
     * @param coupon
     * @return
     */
    public CouponOperationExecution addCoupon(Coupon coupon);

    /**
     * fetch coupon by special price interval
     * @param priceInterval
     * @return
     */
    public CouponOperationExecution getCouponListByPrice(Map<String, BigDecimal> priceInterval);

    /**
     * update coupon by coupon id
     * @param coupon
     * @return
     */
    public CouponOperationExecution updateCouponProfile(Coupon coupon);

    /**
     * delete coupon by coupon id
     * @param couponId
     * @return
     */
    public CouponOperationExecution deleteCouponByCouponId(Long couponId);

    /**
     * delete coupons by coupon ids
     * @param couponIds
     * @return
     */
    public CouponOperationExecution deleteCouponByCouponIds(Long[] couponIds);
}
