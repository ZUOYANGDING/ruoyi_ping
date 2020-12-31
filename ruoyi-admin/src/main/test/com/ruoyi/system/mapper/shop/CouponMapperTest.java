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
import java.util.List;

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

        BigDecimal couponPrice = new BigDecimal("50");
        BigDecimal div = new BigDecimal("100");
        coupon.setCouponPrice(couponPrice.divide(div, 2, RoundingMode.UNNECESSARY));
        coupon.setCouponCode("test coupon code 5");
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

    @Test
    public void selectCouponListTest() {
        Shop shop = shopMapper.selectShopByShopId(100L);
        assertNotNull(shop);
        Coupon coupon = new Coupon();

        coupon.setShop(shop);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startDate = simpleDateFormat.parse("2020-12-19 00:00:00");
            Date endDate = simpleDateFormat.parse("2020-12-31 00:00:00");
            coupon.setStartTime(startDate);
            coupon.setEndTime(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Coupon> couponList = couponMapper.selectCouponList(coupon);
        assertNotNull(couponList);
        couponList.stream().forEach(coupon1 -> {
            System.out.println(coupon1);
        });
    }

    @Test
    public void selectCouponByPriceIntervalTest() {
        BigDecimal couponPriceHigh = new BigDecimal("75");
        BigDecimal couponPriceLow = new BigDecimal("50");
        BigDecimal div = new BigDecimal("100");
        BigDecimal highPrice = couponPriceHigh.divide(div, 2, RoundingMode.UNNECESSARY);
        BigDecimal lowPrice = couponPriceLow.divide(div, 2, RoundingMode.UNNECESSARY);

        List<Coupon> couponList = couponMapper.selectCouponByPriceInterval(highPrice, lowPrice);
        assertNotNull(couponList);
        couponList.stream().forEach(coupon -> {
            System.out.println(coupon);
        });
    }

    @Test
    public void updateCouponTest() {
        Long couponId = 102L;
        Coupon coupon = new Coupon();
        coupon.setCouponId(couponId);
        BigDecimal couponPrice = new BigDecimal("85");
        BigDecimal div = new BigDecimal("100");
        coupon.setCouponPrice(couponPrice.divide(div, 2, RoundingMode.UNNECESSARY));
        coupon.setCouponCode("test coupon code 3 update");
        coupon.setCouponDesc("coupon for shop with ID 100L update");
        coupon.setStartTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startDate = simpleDateFormat.parse("2020-12-19 00:00:00");
            Date endDate = simpleDateFormat.parse("2020-12-29 00:00:00");
            coupon.setStartTime(startDate);
            coupon.setEndTime(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = couponMapper.updateCoupon(coupon);
        assertEquals(result, 1);
    }

    @Test
    public void deleteCouponByCouponIdTest() {
        Long couponId = 104L;
        int result = couponMapper.deleteCouponByCouponId(couponId);
        assertEquals(result, 1);
    }

    @Test
    public void deleteCouponByCouponIdsTest() {
        Long[] couponIds = {102L, 103L};
        int result = couponMapper.deleteCouponByCouponIds(couponIds);
        assertEquals(result, 2);
    }
}