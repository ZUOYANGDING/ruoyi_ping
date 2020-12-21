package com.ruoyi.system.service.shop.imp;

import com.ruoyi.common.enums.shop.ShopPhotoStatusEnum;
import com.ruoyi.common.enums.shop.ShopStatesEnum;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.domain.shop.Shop;
import com.ruoyi.system.dto.ShopOperationExecution;
import com.ruoyi.system.mapper.shop.ShopMapper;
import com.ruoyi.system.mapper.shop.ShopPhotoMapper;
import com.ruoyi.system.service.shop.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.jvm.hotspot.debugger.PageFetcher;

import java.util.Date;
import java.util.List;

/**
 * shop service interface implementation
 *
 * @author zuoyangding
 */
@Service
public class ShopServiceImpl implements ShopService {
    private static final Logger log = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ShopPhotoMapper shopPhotoMapper;


    @Override
    public ShopOperationExecution selectShopList(Shop shop) {
        if (shop == null) {
            return new ShopOperationExecution(ShopStatesEnum.INNER_ERROR);
        }
        ShopOperationExecution soe = null;
        try {
            List<Shop> shopList = shopMapper.selectShopList(shop);
            if (shopList!=null && shopList.size()!=0) {
                soe = new ShopOperationExecution(ShopStatesEnum.SUCCESS, shopList);
            } else {
                soe = new ShopOperationExecution(ShopStatesEnum.NO_SHOP);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
        return soe;
    }

    @Override
    public ShopOperationExecution selectShopById(Long shopId, String status) {
        ShopOperationExecution soe = null;
        if (shopId==null || shopId<=0 || status.equalsIgnoreCase("")) {
           soe = new ShopOperationExecution(ShopStatesEnum.MISSING_ARGS);
        }
        try {
            Shop shop = shopMapper.selectShopByShopId(shopId);
            if (shop!=null && shop.getShopId()>0) {
                if (shop.getStatus().equalsIgnoreCase(status)) {
                    soe = new ShopOperationExecution(ShopStatesEnum.SUCCESS, shop);
                } else {
                    soe = new ShopOperationExecution(ShopStatesEnum.NO_SHOP);
                }
            } else {
                soe = new ShopOperationExecution(ShopStatesEnum.NO_SHOP);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
        return soe;
    }

    @Override
    @Transactional
    public ShopOperationExecution addShop(Shop shop) {
        if (shop==null || shop.getSysUser()==null || shop.getSysUser().getUserId()<1) {
            return new ShopOperationExecution(ShopStatesEnum.INNER_ERROR);
        }
        //set up status, createTime and updateTime
        shop.setStatus("0");
        shop.setCreateTime(new Date());
        shop.setUpdateTime(new Date());

        try {
            int effRows = shopMapper.insertShop(shop);
            if (effRows < 1) {
                return new ShopOperationExecution(ShopStatesEnum.INNER_ERROR);
            } else {
                return new ShopOperationExecution(ShopStatesEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ShopOperationExecution updateShopStates(Shop shop) {
        if (shop==null || shop.getShopId()==null || shop.getShopId()<1) {
            return new ShopOperationExecution(ShopStatesEnum.INNER_ERROR);
        }

        if (shop.getStatus()==null || shop.getStatus().equalsIgnoreCase("")) {
            return new ShopOperationExecution(ShopStatesEnum.MISSING_ARGS);
        }

        try {
            int effRows = shopMapper.updateShopStatus(shop);
            if (effRows < 0) {
                return new ShopOperationExecution(ShopStatesEnum.INNER_ERROR);
            } else {
                return new ShopOperationExecution(ShopStatesEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ShopOperationExecution updateShopProfile(Shop shop) {
        if (shop==null || shop.getShopId()==null || shop.getShopId()<1) {
            return new ShopOperationExecution(ShopStatesEnum.INNER_ERROR);
        }

        try {
            int effRows = shopMapper.updateShop(shop);
            if (effRows < 0) {
                return new ShopOperationExecution(ShopStatesEnum.INNER_ERROR);
            } else {
                return new ShopOperationExecution(ShopStatesEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ShopOperationExecution deleteShopByIds(Long[] shopIds) {
        if (shopIds==null || shopIds.length==0) {
            return new ShopOperationExecution(ShopStatesEnum.INNER_ERROR);
        }

        try {
            // delete related photos from db
            int effRows_photo = shopPhotoMapper.deleteShopPhotoByShopIds(shopIds);
            int effRows = shopMapper.deleteShopByShopIds(shopIds);
            if (effRows < 0 || effRows_photo<0) {
                return new ShopOperationExecution(ShopStatesEnum.INNER_ERROR);
            } else {
                return new ShopOperationExecution(ShopStatesEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ShopOperationExecution deleteShopById(Long shopId) {
        if (shopId==null || shopId<1) {
            return new ShopOperationExecution(ShopStatesEnum.INNER_ERROR);
        }

        try {
            // delete related photos from db
            int effRows_photo = shopPhotoMapper.deleteShopPhotoByShopId(shopId);
            int effRows = shopMapper.deleteShopByShopId(shopId);
            if (effRows < 0 || effRows_photo<0) {
                return new ShopOperationExecution(ShopStatesEnum.INNER_ERROR);
            } else {
                return new ShopOperationExecution(ShopStatesEnum.SUCCESS);
            }
        } catch (RuntimeException e) {
            throw new CustomException(e.getMessage());
        }
    }
}
