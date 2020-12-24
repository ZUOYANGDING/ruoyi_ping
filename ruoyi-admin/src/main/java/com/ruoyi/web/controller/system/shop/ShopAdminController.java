package com.ruoyi.web.controller.system.shop;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.shop.Shop;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop/admin")
public class ShopAdminController {
    @PostMapping("/list")
    public AjaxResult filterShop(@RequestBody Shop shop) {
        return null;
    }

    @PostMapping("/updatestatus")
    public AjaxResult changeShopStatus(@RequestBody Shop shop) {
        return null;
    }
}
