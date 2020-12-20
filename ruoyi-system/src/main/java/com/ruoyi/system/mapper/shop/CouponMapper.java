package com.ruoyi.system.mapper.shop;

import com.ruoyi.system.domain.shop.Coupon;
import com.ruoyi.system.domain.shop.Shop;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.util.List;

public interface CouponMapper {
    public Coupon selectCouponByCouponId(Long couponId);

    public int insertCoupon(Coupon coupon);

    public List<Coupon> selectCouponList(Coupon coupon);

    public List<Coupon> selectCouponByPriceInterval(@Param("highPrice") BigDecimal high, @Param("lowPrice") BigDecimal low);

    public int updateCoupon(Coupon coupon);

    public int deleteCouponByCouponId(Long couponId);

    public int deleteCouponByCouponIds(Long[] couponIds);
}
