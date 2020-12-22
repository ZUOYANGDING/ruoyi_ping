package com.ruoyi.system.service.shop;

import com.ruoyi.system.domain.shop.CouponPhoto;
import com.ruoyi.system.dto.CouponPhotoOperationExecution;

import java.util.List;

/**
 * coupon photo service interface
 *
 * @author zuoyangding
 */
public interface CouponPhotoService {
    /**
     * fetch coupon photo by photo id
     * @param photoId
     * @return
     */
    public CouponPhotoOperationExecution selectCouponPhotoById(Long photoId);

    /**
     * fetch coupon photos by filter
     * @param couponId
     * @return
     */
    public CouponPhotoOperationExecution selectCouponPhotoListByCouponId(Long couponId);

    /**
     * add single coupon photo
     * @param couponPhoto
     * @return
     */
    public CouponPhotoOperationExecution addCouponPhoto(CouponPhoto couponPhoto);

    /**
     * add list of coupon photos
     * @param couponPhotos
     * @return
     */
    public CouponPhotoOperationExecution addCouponPhotos(List<CouponPhoto> couponPhotos);

    /**
     * delete coupon photo by photo id
     * @param photoId
     * @return
     */
    public CouponPhotoOperationExecution deleteCouponPhotoById(Long photoId);

    /**
     * delete coupon photos by photo ids
     * @param photoIds
     * @return
     */
    public CouponPhotoOperationExecution deleteCouponPhotoByIds(Long[] photoIds);
}
