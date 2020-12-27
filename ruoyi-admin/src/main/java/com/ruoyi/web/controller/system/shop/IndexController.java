package com.ruoyi.web.controller.system.shop;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.shop.CouponService;
import com.ruoyi.system.service.shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @Autowired
    ShopService shopService;

    @Autowired
    ISysUserService userService;

    @Autowired
    CouponService couponService;

    public AjaxResult fetchCoupons(){
        return null;
    }
}
