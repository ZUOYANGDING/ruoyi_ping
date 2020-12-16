package com.ruoyi.system.mapper.shop;

import com.ruoyi.system.domain.shop.ShopPhoto;

import java.util.List;

public interface ShopPhotoMapper {
    public ShopPhoto getShopPhotoById(Long shopPhotoId);

    public List<ShopPhoto> getShopPhotoListByShopId(Long shopId);

    public int insertShopPhoto(ShopPhoto shopPhoto);

    public int batchInsertShopPhoto(List<ShopPhoto> shopPhotos);
}
