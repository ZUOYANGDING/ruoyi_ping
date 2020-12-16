package com.ruoyi.system.mapper.shop;

import com.ruoyi.system.domain.shop.Shop;

public interface ShopMapper {
    public Shop selectShopByShopId(Long shopId);

    public int insertShop(Shop shop);

}
