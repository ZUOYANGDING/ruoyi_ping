package com.ruoyi.system.mapper.shop;

import com.ruoyi.system.domain.shop.CouponPhoto;

import java.util.List;

public interface CouponPhotoMapper {
    public CouponPhoto getCouponPhotoById(Long photoId);

    public List<CouponPhoto> getCouponPhotoByCouponId(Long couponId);

    public int insertCouponPhoto(CouponPhoto couponPhoto);

    public int batchInsertCouponPhoto(List<CouponPhoto> couponPhotos);

    public int deleteCouponPhotoById(Long photoId);

    public int deleteCouponPhotoByIds(Long[] photoIds);

    public int deleteCouponPhotoByCouponId(Long couponId);

    public int deleteCouponPhotoByCouponIds(Long[] couponIds);
}
