package com.ruoyi.system.service.shop;

import com.ruoyi.system.domain.shop.ShopPhoto;
import com.ruoyi.system.dto.ShopPhotoOperationExecution;

import java.util.List;

/**
 * shop photo service interface
 *
 * @author zuoyangding
 */
public interface ShopPhotoService {
    /**
     * select shop photo by photo id
     * @param photoId
     * @return
     */
    public ShopPhotoOperationExecution selectShopPhotoById(Long photoId);

    /**
     * select shop photo by shop id
     * @param shopId
     * @return
     */
    public ShopPhotoOperationExecution selectShopPhotoListByShopId(Long shopId);

    /**
     * add single shop photo
     * @param shopPhoto
     * @return
     */
    public ShopPhotoOperationExecution addShopPhoto(ShopPhoto shopPhoto);

    /**
     * add shop photos
     * @param shopPhotos
     * @return
     */
    public ShopPhotoOperationExecution addShopPhotos(List<ShopPhoto> shopPhotos);

    /**
     * delete shop photo by photo id
     * @param photoId
     * @return
     */
    public ShopPhotoOperationExecution deleteShopPhotoById(Long photoId);

    /**
     * delete shop photos by photo ids
     * @param photoIds
     * @return
     */
    public ShopPhotoOperationExecution deleteShopPhotoByIds(Long[] photoIds);

    /**
     * delete shop photos by shop id
     * @param shopId
     * @return
     */
    public ShopPhotoOperationExecution deleteShopPhotoByShopId(Long shopId);

    /**
     * delete shop photos by shop ids
     * @param shopIds
     * @return
     */
    public ShopPhotoOperationExecution deleteShopPhotoByShopIds(Long[] shopIds);
}
