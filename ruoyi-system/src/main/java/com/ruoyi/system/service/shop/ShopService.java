package com.ruoyi.system.service.shop;

import com.ruoyi.system.domain.shop.Shop;

import java.util.List;

public interface ShopService {
    public List<Shop> selectShopList(Shop shop);

    public Shop selectShopByName(String shopName);

    public Shop selectShopById(Long shopId);

    public int insertShop(Shop shop);

    public int updateShopStates(Shop shop);

    public int updateShopProfile(Shop shop);

    public int deleteShopByIds(Long[] shopIds);

    public int deleteShopById(Long shopId);
}
