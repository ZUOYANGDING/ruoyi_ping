package com.ruoyi.system.service.shop.imp;

import com.ruoyi.common.enums.shop.ShopPhotoStatusEnum;
import com.ruoyi.common.enums.shop.ShopStatesEnum;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.domain.shop.ShopPhoto;
import com.ruoyi.system.dto.ShopPhotoOperationExecution;
import com.ruoyi.system.mapper.shop.ShopPhotoMapper;
import com.ruoyi.system.service.shop.ShopPhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * shop photo service implementation
 *
 * @author zuoyangding
 */
@Service
public class ShopPhotoServiceImpl implements ShopPhotoService {
    public static final Logger log = LoggerFactory.getLogger(ShopPhotoServiceImpl.class);

    @Autowired
    ShopPhotoMapper shopPhotoMapper;


    @Override
    public ShopPhotoOperationExecution selectShopPhotoById(Long photoId) {
        if (photoId==null || photoId < 1) {
            return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.INNER_ERROR);
        }

        try {
            ShopPhoto shopPhoto = shopPhotoMapper.getShopPhotoById(photoId);
            if (shopPhoto!=null && shopPhoto.getPhotoId()>0) {
                return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.SUCCESS, shopPhoto);
            } else {
                return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.NO_PHOTO);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public ShopPhotoOperationExecution selectShopPhotoListByShopId(Long shopId) {
        if (shopId==null || shopId<1) {
            return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.INNER_ERROR);
        }

        try {
            List<ShopPhoto> shopPhotos = shopPhotoMapper.getShopPhotoListByShopId(shopId);
            if (shopPhotos!=null && shopPhotos.size()>1) {
                return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.SUCCESS, shopPhotos);
            } else {
                return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.NO_PHOTO);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ShopPhotoOperationExecution addShopPhoto(ShopPhoto shopPhoto) {
        if (shopPhoto == null || shopPhoto.getShopId()==null || shopPhoto.getShopId()<1) {
            return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.INNER_ERROR);
        }

        try {
            int effRow = shopPhotoMapper.insertShopPhoto(shopPhoto);
            if (effRow < 1) {
                return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.INNER_ERROR);
            } else {
                return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ShopPhotoOperationExecution addShopPhotos(List<ShopPhoto> shopPhotos) {
        if (shopPhotos==null || shopPhotos.size()<1) {
            return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.INNER_ERROR);
        }

        try {
            int effRow = shopPhotoMapper.batchInsertShopPhoto(shopPhotos);
            if (effRow < 1) {
                return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.INNER_ERROR);
            } else {
                return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ShopPhotoOperationExecution deleteShopPhotoById(Long photoId) {
        if (photoId==null || photoId<1) {
            return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.INNER_ERROR);
        }

        try {
            int effRow = shopPhotoMapper.deleteShopPhotoById(photoId);
            if (effRow < 0) {
                return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.INNER_ERROR);
            } else {
                return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ShopPhotoOperationExecution deleteShopPhotoByIds(Long[] photoIds) {
        if (photoIds==null || photoIds.length<1) {
            return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.INNER_ERROR);
        }

        try {
            int effRow = shopPhotoMapper.deleteShopPhotoByIds(photoIds);
            if (effRow < 0) {
                return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.INNER_ERROR);
            } else {
                return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ShopPhotoOperationExecution deleteShopPhotoByShopId(Long shopId) {
        if (shopId==null || shopId<1) {
            return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.INNER_ERROR);
        }

        try {
            int effRow = shopPhotoMapper.deleteShopPhotoByShopId(shopId);
            if (effRow < 0) {
                return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.INNER_ERROR);
            } else {
                return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ShopPhotoOperationExecution deleteShopPhotoByShopIds(Long[] shopIds) {
        if (shopIds==null || shopIds.length<1) {
            return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.INNER_ERROR);
        }

        try {
            int effRow = shopPhotoMapper.deleteShopPhotoByShopIds(shopIds);
            if (effRow < 0) {
                return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.INNER_ERROR);
            } else {
                return new ShopPhotoOperationExecution(ShopPhotoStatusEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }
}
