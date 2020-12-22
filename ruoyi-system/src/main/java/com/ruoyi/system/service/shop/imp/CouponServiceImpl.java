package com.ruoyi.system.service.shop.imp;

import com.ruoyi.common.enums.shop.CouponStatusEnum;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.domain.shop.Coupon;
import com.ruoyi.system.dto.CouponOperationExecution;
import com.ruoyi.system.mapper.shop.CouponMapper;
import com.ruoyi.system.mapper.shop.CouponPhotoMapper;
import com.ruoyi.system.service.shop.CouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * coupon service implementation
 *
 * @author zuoyangding
 */
@Service
public class CouponServiceImpl implements CouponService {

    public static final Logger log = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    CouponPhotoMapper couponPhotoMapper;

    @Override
    public CouponOperationExecution getCouponByCouponId(Long couponId) {
        if (couponId==null || couponId < 1) {
            return new CouponOperationExecution(CouponStatusEnum.INNER_ERROR);
        }

        try {
            Coupon coupon = couponMapper.selectCouponByCouponId(couponId);
            if (coupon!=null && coupon.getCouponId()>0) {
                return new CouponOperationExecution(CouponStatusEnum.SUCCESS, coupon);
            } else {
                return new CouponOperationExecution(CouponStatusEnum.NO_COUPON);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public CouponOperationExecution getCouponList(Coupon coupon) {
        if (coupon == null) {
            return new CouponOperationExecution(CouponStatusEnum.INNER_ERROR);
        }

        try {
            List<Coupon> couponList = couponMapper.selectCouponList(coupon);
            if (couponList!=null && couponList.size()>0) {
                return new CouponOperationExecution(CouponStatusEnum.SUCCESS, couponList);
            } else {
                return new CouponOperationExecution(CouponStatusEnum.NO_COUPON);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CouponOperationExecution addCoupon(Coupon coupon) {
        if (coupon==null || coupon.getShop()==null || coupon.getShop().getShopId()<1) {
            return new CouponOperationExecution(CouponStatusEnum.INNER_ERROR);
        }

        try {
            int effRow = couponMapper.insertCoupon(coupon);
            if (effRow < 1) {
                return new CouponOperationExecution(CouponStatusEnum.INNER_ERROR);
            } else {
                return new CouponOperationExecution(CouponStatusEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public CouponOperationExecution getCouponListByPrice(Map<String, BigDecimal> priceInterval) {
        if (priceInterval==null) {
            return new CouponOperationExecution(CouponStatusEnum.INNER_ERROR);
        }

        //set up high bound and low bound
        BigDecimal high = priceInterval.get("high");
        BigDecimal low = priceInterval.get("low");
        if (high==null && low==null) {
            high = new BigDecimal("100");
            low = new BigDecimal("0");
        } else if (high!=null && low==null){
            low = new BigDecimal("0");
        } else if (high==null && low!=null){
            high = new BigDecimal("100");
        }

        log.debug("low bound {}", low);
        log.debug("high bound {}", high);
        try {
            List<Coupon> couponList = couponMapper.selectCouponByPriceInterval(high, low);
            if (couponList!=null && couponList.size()>0) {
                return new CouponOperationExecution(CouponStatusEnum.SUCCESS, couponList);
            } else {
                return new CouponOperationExecution(CouponStatusEnum.NO_COUPON);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CouponOperationExecution updateCouponProfile(Coupon coupon) {
        if (coupon==null || coupon.getCouponId()==null || coupon.getCouponId()<1) {
            return new CouponOperationExecution(CouponStatusEnum.SUCCESS);
        }

        try {
            int effRow = couponMapper.updateCoupon(coupon);
            if (effRow < 0) {
                return new CouponOperationExecution(CouponStatusEnum.INNER_ERROR);
            } else {
                return new CouponOperationExecution(CouponStatusEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CouponOperationExecution deleteCouponByCouponId(Long couponId) {
        if (couponId==null || couponId<1) {
            return new CouponOperationExecution(CouponStatusEnum.INNER_ERROR);
        }

        try {
            int effRow_photo = couponPhotoMapper.deleteCouponPhotoByCouponId(couponId);
            int effRow = couponMapper.deleteCouponByCouponId(couponId);
            if (effRow<0 || effRow_photo<0) {
                return new CouponOperationExecution(CouponStatusEnum.INNER_ERROR);
            } else {
                return new CouponOperationExecution(CouponStatusEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CouponOperationExecution deleteCouponByCouponIds(Long[] couponIds) {
        if (couponIds==null || couponIds.length<1) {
            return new CouponOperationExecution(CouponStatusEnum.INNER_ERROR);
        }

        try {
            int effRow_photo = couponPhotoMapper.deleteCouponPhotoByCouponIds(couponIds);
            int effRow = couponMapper.deleteCouponByCouponIds(couponIds);
            if (effRow_photo<0 || effRow<0) {
                return new CouponOperationExecution(CouponStatusEnum.INNER_ERROR);
            } else {
                return new CouponOperationExecution(CouponStatusEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }
}
