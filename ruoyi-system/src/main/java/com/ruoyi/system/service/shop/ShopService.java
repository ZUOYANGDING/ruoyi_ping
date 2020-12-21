package com.ruoyi.system.service.shop;

import com.ruoyi.system.domain.shop.Shop;
import com.ruoyi.system.dto.ShopOperationExecution;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * shop service interface
 *
 * @author zuoyangding
 */

public interface ShopService {
    /**
     * fetch shop list based on filter
     * @param shop
     * @return
     */
    public ShopOperationExecution selectShopList(Shop shop);

    /**
     * fetch shop by shop id, only return status==1
     * @param shopId
     * @param status
     * @return
     */
    public ShopOperationExecution selectShopById(Long shopId, String status);

    /**
     * create new shop, with default status==0
     * @param shop
     * @return
     */
    public ShopOperationExecution addShop(Shop shop);

    /**
     * update shop status by shopId
     * @param shop
     * @return
     */
    public ShopOperationExecution updateShopStates(Shop shop);

    /**
     * update shop status, name, desc or address
     * @param shop
     * @return
     */
    public ShopOperationExecution updateShopProfile(Shop shop);

    /**
     * batch delete shops from db
     * @param shopIds
     * @return
     */
    public ShopOperationExecution deleteShopByIds(Long[] shopIds);

    /**
     * delete shop from db
     * @param shopId
     * @return
     */
    public ShopOperationExecution deleteShopById(Long shopId);
}
