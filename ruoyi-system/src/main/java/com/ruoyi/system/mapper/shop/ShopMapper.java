package com.ruoyi.system.mapper.shop;

import com.ruoyi.system.domain.shop.Shop;

import java.util.List;

/**
 * shop interface
 *
 * @author zuoyangding
 */

public interface ShopMapper {
    /**
     * Get unique shop each time
     * @param shopId
     * @return
     */
    public Shop selectShopByShopId(Long shopId);

    /**
     * create one shop each time
     * @param shop
     * @return
     */
    public int insertShop(Shop shop);

    /**
     * get one shop each time
     * @param shopName
     * @return
     */
    public Shop selectShopByShopName(String shopName);


    /**
     * get list of shops
     * @param shop
     * @return
     */
    public List<Shop> selectShopList(Shop shop);


    /**
     * return number of shops have the same name
     * @param shopName
     * @return
     */
    public int checkShopNameUnique(String shopName);

    /**
     * update on shop each time
     * @param shop
     * @return
     */
    public int updateShop(Shop shop);


    /**
     * update shop status by shop Id
     * @param shop
     * @return
     */
    public int updateShopStatus(Shop shop);

    /**
     * delete shop by id
     * @param shopId
     * @return
     */
    public int deleteShopByShopId(Long shopId);


    /**
     * batch delete shops
     * @param shopIds
     * @return
     */
    public int deleteShopByShopIds(Long[] shopIds);

}
