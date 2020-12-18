package com.ruoyi.system.mapper.shop;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.domain.shop.Coupon;
import com.ruoyi.system.domain.shop.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class CouponMapperTest {
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    ShopMapper shopMapper;

    @Test
    public void insertCouponTest() {
        Shop shop = shopMapper.selectShopByShopId(100L);
        assertNotNull(shop);
        Coupon coupon = new Coupon();

        BigDecimal couponPrice = new BigDecimal("25");
        BigDecimal div = new BigDecimal("100");
        coupon.setCouponPrice(couponPrice.divide(div, 2, RoundingMode.UNNECESSARY));
        coupon.setCouponCode("test coupon code");
        coupon.setCouponDesc("coupon for shop with ID 100L");
        coupon.setStartTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse("2020-12-31 00:00:00");
            coupon.setEndTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        coupon.setShop(shop);

        int result = couponMapper.insertCoupon(coupon);
        assertEquals(1, result);
    }

    @Test
    public void selectCouponByCouponIdTest() {
        Long couponId = 100L;
        Coupon coupon =couponMapper.selectCouponByCouponId(couponId);
        assertNotNull(coupon);
        System.out.print(coupon);
    }
}