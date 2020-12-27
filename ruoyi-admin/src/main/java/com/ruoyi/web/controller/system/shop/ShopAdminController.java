package com.ruoyi.web.controller.system.shop;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.shop.ShopStatesEnum;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.domain.shop.Shop;
import com.ruoyi.system.dto.ShopOperationExecution;
import com.ruoyi.system.service.shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop/admin")
public class ShopAdminController {

    @Autowired
    ShopService shopService;

    @PostMapping("/list")
    @PreAuthorize("@ss.hasPermi('shop:admin:list')")
    public AjaxResult filterShop(@RequestBody Shop shop) {
        return null;
    }


    /**
     * for checking shop and change status for shop
     * @param shop
     * @return
     */
    @PutMapping("/updatestatus")
    @PreAuthorize("@ss.hasPermi('shop:admin:status')")
    public AjaxResult changeShopStatus(@RequestBody Shop shop) {
        if (shop==null || shop.getShopId()==null || shop.getShopId()<1) {
            return AjaxResult.error(501, "Missing Required arguments");
        }

        try {
            ShopOperationExecution soe = shopService.updateShopStates(shop);
            if (soe.getState() == ShopStatesEnum.SUCCESS.getState()) {
                return AjaxResult.success();
            } else if (soe.getState() == ShopStatesEnum.MISSING_ARGS.getState()) {
                return AjaxResult.error(501, "Missing Required arguments");
            } else {
                return AjaxResult.error(ShopStatesEnum.INNER_ERROR.getInfo());
            }
        } catch (CustomException e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}
