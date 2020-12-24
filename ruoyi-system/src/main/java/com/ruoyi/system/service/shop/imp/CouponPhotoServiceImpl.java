package com.ruoyi.system.service.shop.imp;

import com.ruoyi.common.enums.shop.CouponPhotoStatusEnum;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.domain.shop.CouponPhoto;
import com.ruoyi.system.dto.CouponPhotoOperationExecution;
import com.ruoyi.system.mapper.shop.CouponPhotoMapper;
import com.ruoyi.system.service.shop.CouponPhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * coupon photo service implementation
 *
 * @author zuoyangding
 */
@Service
public class CouponPhotoServiceImpl implements CouponPhotoService {
    private static final Logger log = LoggerFactory.getLogger(CouponPhotoServiceImpl.class);

    @Autowired
    CouponPhotoMapper couponPhotoMapper;

    @Override
    public CouponPhotoOperationExecution selectCouponPhotoById(Long photoId) {
        if (photoId==null ||  photoId<1) {
            return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.INNER_ERROR);
        }

        try {
            CouponPhoto couponPhoto = couponPhotoMapper.getCouponPhotoById(photoId);
            if (couponPhoto==null || couponPhoto.getPhotoId()==null || couponPhoto.getPhotoId()<1) {
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.NO_PHOTO);
            } else {
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.SUCCESS, couponPhoto);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public CouponPhotoOperationExecution selectCouponPhotoListByCouponId(Long couponId) {
        if (couponId==null || couponId<1) {
            return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.INNER_ERROR);
        }

        try {
            List<CouponPhoto> couponPhotos = couponPhotoMapper.getCouponPhotoByCouponId(couponId);
            if (couponPhotos==null || couponPhotos.size()<1) {
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.NO_PHOTO);
            } else {
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.SUCCESS, couponPhotos);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CouponPhotoOperationExecution addCouponPhoto(CouponPhoto couponPhoto) {
        if (couponPhoto==null || couponPhoto.getCouponId()==null || couponPhoto.getCouponId()<1) {
            return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.INNER_ERROR);
        }

        try {
            int effRow = couponPhotoMapper.insertCouponPhoto(couponPhoto);
            if (effRow < 1) {
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.INNER_ERROR);
            } else {
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CouponPhotoOperationExecution addCouponPhotos(List<CouponPhoto> couponPhotos) {
        if (couponPhotos==null || couponPhotos.size()<1) {
            return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.INNER_ERROR);
        }

        try {
            int effRow = couponPhotoMapper.batchInsertCouponPhoto(couponPhotos);
            if (effRow < 1) {
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.INNER_ERROR);
            } else {
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CouponPhotoOperationExecution deleteCouponPhotoById(Long photoId) {
        if (photoId==null || photoId<1) {
            return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.INNER_ERROR);
        }

        try {
            int effRow = couponPhotoMapper.deleteCouponPhotoById(photoId);
            if (effRow < 0) {
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.INNER_ERROR);
            } else {
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CouponPhotoOperationExecution deleteCouponPhotoByIds(Long[] photoIds) {
        if (photoIds==null || photoIds.length<1) {
            return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.INNER_ERROR);
        }

        try {
            int effRow = couponPhotoMapper.deleteCouponPhotoByIds(photoIds);
            if (effRow < 0) {
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.INNER_ERROR);
            } else {
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CouponPhotoOperationExecution deleteCouponPhotoByCouponId(Long couponId) {
        if (couponId==null || couponId<1) {
            return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.INNER_ERROR);
        }

        try {
            int effRow = couponPhotoMapper.deleteCouponPhotoByCouponId(couponId);
            if (effRow < 0) {
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.INNER_ERROR);
            } else if (effRow == 1){
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.NO_PHOTO);
            } else {
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CouponPhotoOperationExecution deleteCouponPhotoByCouponIds(Long[] couponIds) {
        if (couponIds==null || couponIds.length<1) {
            return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.INNER_ERROR);
        }

        try {
            int effRow = couponPhotoMapper.deleteCouponPhotoByCouponIds(couponIds);
            if (effRow < 0) {
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.INNER_ERROR);
            } else if (effRow == 1){
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.NO_PHOTO);
            } else {
                return new CouponPhotoOperationExecution(CouponPhotoStatusEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }
}
