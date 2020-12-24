package com.ruoyi.system.service.shop.imp;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.enums.shop.CouponStatusEnum;
import com.ruoyi.system.domain.shop.Coupon;
import com.ruoyi.system.domain.shop.Shop;
import com.ruoyi.system.dto.CouponOperationExecution;
import com.ruoyi.system.dto.ShopOperationExecution;
import com.ruoyi.system.service.shop.CouponService;
import com.ruoyi.system.service.shop.ShopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class CouponServiceImplTest {
    @Autowired
    CouponService couponService;
    @Autowired
    ShopService shopService;

    @Test
    public void getCouponByCouponIdTest() {
        Long couponId = 100L;
        CouponOperationExecution coe = couponService.getCouponByCouponId(couponId);
        assertNotNull(coe);
        System.out.println(coe.getCoupon());
    }

    @Test
    public void getCouponListTest() {
//        Shop shop = new Shop();
//        shop.setShopId(100L);
        Coupon coupon = new Coupon();
        coupon.setCouponCode("test coupon code");
//        coupon.setShop(shop);
        CouponOperationExecution coe = couponService.getCouponList(coupon);
        assertNotNull(coe);
        coe.getCouponList().stream().forEach(coupon1 -> {
            System.out.println(coupon1);
        });
    }

    @Test
    public void addCoupon() {
        Coupon coupon = new Coupon();
        Shop shop = shopService.selectShopById(101L, "0").getShop();
        assertNotNull(shop);
        coupon.setShop(shop);
        coupon.setCouponCode("test coupon code 3");
        coupon.setCouponDesc("test coupon with ID 101L");

        BigDecimal price = new BigDecimal("55");
        BigDecimal div = new BigDecimal("100");
        BigDecimal couponPrice = price.divide(div, 2, RoundingMode.UNNECESSARY);
        coupon.setCouponPrice(couponPrice);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startTime = sdf.parse("2020-12-25 00:00:00");
            Date endTime = sdf.parse("2021-1-10 00:00:00");
            coupon.setStartTime(startTime);
            coupon.setEndTime(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        CouponOperationExecution coe = couponService.addCoupon(coupon);
        assertNotNull(coe);
        assertEquals(coe.getState(), CouponStatusEnum.SUCCESS.getState());
    }

    @Test
    public void getCouponListByPriceTest() {
        Map<String, BigDecimal> priceInterval = new HashMap();
        priceInterval.put("high", null);
        priceInterval.put("low", null);
        // test with out price interval
        CouponOperationExecution coe = couponService.getCouponListByPrice(priceInterval);
        assertNotNull(coe);
        coe.getCouponList().stream().forEach(coupon -> {
            System.out.println(coupon);
        });
        System.out.println("=============================================");
        //test with high bound
        BigDecimal highPrice = new BigDecimal("50");
        priceInterval.put("high", highPrice);
        coe = couponService.getCouponListByPrice(priceInterval);
        assertNotNull(coe);
        coe.getCouponList().stream().forEach(coupon -> {
            System.out.println(coupon);
        });
        System.out.println("=============================================");
        //test with low bound
        BigDecimal lowBound = new BigDecimal("50");
        priceInterval.put("high", null);
        priceInterval.put("low", lowBound);
        coe = couponService.getCouponListByPrice(priceInterval);
        assertNotNull(coe);
        coe.getCouponList().stream().forEach(coupon -> {
            System.out.println(coupon);
        });
        System.out.println("=============================================");
        //test with both
        highPrice = new BigDecimal("75");
        priceInterval.put("high", highPrice);
        coe = couponService.getCouponListByPrice(priceInterval);
        assertNotNull(coe);
        coe.getCouponList().stream().forEach(coupon -> {
            System.out.println(coupon);
        });
    }

    @Test
    public void updateCouponProfile() {
        Coupon coupon = new Coupon();
        Long couponId = 105L;
        BigDecimal couponPrice = new BigDecimal("33");
        BigDecimal div = new BigDecimal("100");
        coupon.setCouponPrice(couponPrice.divide(div, 2, RoundingMode.UNNECESSARY));
        coupon.setCouponCode("updated coupon code");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-HH-dd HH:mm:ss");
            Date startTime = sdf.parse("2020-12-26 11:59:59");
            Date endTime = sdf.parse("2021-1-10 12:00:00");
            coupon.setStartTime(startTime);
            coupon.setEndTime(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        CouponOperationExecution coe = couponService.updateCouponProfile(coupon);
        assertNotNull(coe);
        assertEquals(coe.getState(), CouponStatusEnum.SUCCESS.getState());
    }

    @Test
    public void deleteCouponByCouponIdTest() {
        Long couponId = 106L;
        CouponOperationExecution coe = couponService.deleteCouponByCouponId(couponId);
        assertNotNull(coe);
        assertEquals(coe.getState(), CouponStatusEnum.SUCCESS.getState());
    }

    @Test
    public void deleteCouponByCouponIdsTest() {
        Long[] couponIds = {105L, 106L};
        CouponOperationExecution coe = couponService.deleteCouponByCouponIds(couponIds);
        assertNotNull(coe);
        assertEquals(coe.getState(), CouponStatusEnum.SUCCESS.getState());
    }
}