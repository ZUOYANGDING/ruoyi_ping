package com.ruoyi.web.controller.system.shop;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.CouponBody;
import com.ruoyi.common.enums.shop.CouponStatusEnum;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.domain.shop.Coupon;
import com.ruoyi.system.domain.shop.Shop;
import com.ruoyi.system.dto.CouponOperationExecution;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.shop.CouponService;
import com.ruoyi.system.service.shop.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * index page controller
 *
 * @author zuoyangding
 */

@RestController
@RequestMapping("/index")
public class IndexController {
    public static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    ShopService shopService;

    @Autowired
    ISysUserService userService;

    @Autowired
    CouponService couponService;

    @GetMapping("/all")
    public AjaxResult fetchAllCoupon(){
        Coupon coupon  = new Coupon();
        try {
            CouponOperationExecution coe = couponService.getCouponList(coupon);
            if (coe.getState() == CouponStatusEnum.NO_COUPON.getState()) {
                return AjaxResult.success(CouponStatusEnum.NO_COUPON.getInfo());
            } else if (coe.getState() == CouponStatusEnum.SUCCESS.getState()) {
                return AjaxResult.success(coe.getCouponList());
            } else {
                return AjaxResult.error(coe.getStateInfo());
            }
        } catch (CustomException e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    @PostMapping("/filter")
    public AjaxResult fetchCouponByFilter(@RequestBody CouponBody couponBody) {
        Coupon coupon;
        if (couponBody == null) {
           coupon = new Coupon();
        } else {
            try {
                coupon = transferCouponBodyToCoupon(couponBody);
//                log.debug("couponBody {}", coupon);
            } catch (RuntimeException e) {
                return AjaxResult.error(500, e.getMessage());
            }
        }
        try {
            // filter coupon list by coupon info except the price intervals
            CouponOperationExecution coe = couponService.getCouponList(coupon);
            if (coe.getState() == CouponStatusEnum.INNER_ERROR.getState()) {
                return AjaxResult.error(coe.getStateInfo());
            } else if (coe.getState() == CouponStatusEnum.NO_COUPON.getState()) {
                return AjaxResult.success(coe.getStateInfo());
            } else {
                if (couponBody.getHigh() != null || couponBody.getLow() != null) {
                    Map<String, BigDecimal> priceInterval = new HashMap<>();
                    if (couponBody.getHigh() != null) {
                        BigDecimal highPrice = new BigDecimal(couponBody.getHigh());
                        priceInterval.put("high", highPrice);
                    }
                    if (couponBody.getLow() != null) {
                        BigDecimal lowPrice = new BigDecimal(couponBody.getLow());
                        priceInterval.put("low", lowPrice);
                    }

                    // filter coupon list by coupon price interval
                    CouponOperationExecution coe_by_price = couponService.getCouponListByPrice(priceInterval);
                    if (coe_by_price.getState() == CouponStatusEnum.INNER_ERROR.getState()) {
                        return AjaxResult.error(coe_by_price.getStateInfo());
                    } else if (coe_by_price.getState() == CouponStatusEnum.NO_COUPON.getState()) {
                        return AjaxResult.success(coe.getCouponList());
                    } else {
//                        log.debug("filter by coupon body{} ", coe.getCouponList());

                        // get the interception both upon filters
                        List<Coupon> result = coe.getCouponList().stream()
                                .filter(coe_coupon ->
                                        coe_by_price.getCouponList().stream().map(Coupon::getCouponId).anyMatch(
                                                couponId -> Objects.equals(coe_coupon.getCouponId(), couponId)
                                        )
                                ).collect(Collectors.toList());

//                        log.debug("filtered result {}", result);

                        if (result == null || result.size() < 1) {
                            return AjaxResult.success(CouponStatusEnum.NO_COUPON.getInfo());
                        } else {
                            return AjaxResult.success(result);
                        }
                    }
                } else {
                    return AjaxResult.success(coe.getCouponList());
                }
            }
        } catch (CustomException e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * transfer http body to coupon entity
     * @param couponBody
     * @return
     * @throws RuntimeException
     */
    private Coupon transferCouponBodyToCoupon(CouponBody couponBody) throws RuntimeException{
        Coupon coupon = new Coupon();
        if (couponBody.getCouponId() != null) {
            coupon.setCouponId(couponBody.getCouponId());
        }
        if (couponBody.getShopId() != null) {
            Shop shop = new Shop();
            shop.setShopId(couponBody.getShopId());
            coupon.setShop(shop);
        }
        if (couponBody.getCouponCode() != null) {
            coupon.setCouponCode(couponBody.getCouponCode());
        }
        if (couponBody.getCouponDesc() != null) {
            coupon.setCouponDesc(couponBody.getCouponDesc());
        }
        if (couponBody.getCouponPrice() != null) {
            BigDecimal price = new BigDecimal(couponBody.getCouponPrice());
            BigDecimal div = new BigDecimal("100");
            price = price.divide(div, 2, RoundingMode.UNNECESSARY);
            coupon.setCouponPrice(price);
        }
        if (couponBody.getStartTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = null;
            try {
                startTime = sdf.parse(couponBody.getStartTime());
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage());
            }
            coupon.setStartTime(startTime);
        }
        if (couponBody.getEndTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date endTime = null;
            try {
                endTime = sdf.parse(couponBody.getEndTime());
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage());
            }
            coupon.setEndTime(endTime);
        }
        return coupon;
    }
}
