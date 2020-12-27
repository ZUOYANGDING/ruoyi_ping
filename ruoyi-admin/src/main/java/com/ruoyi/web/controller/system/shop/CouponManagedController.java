package com.ruoyi.web.controller.system.shop;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.CouponBody;
import com.ruoyi.common.core.domain.model.PhotoAddBody;
import com.ruoyi.common.enums.shop.CouponPhotoStatusEnum;
import com.ruoyi.common.enums.shop.CouponStatusEnum;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.file.shop.ShopImgFileUtil;
import com.ruoyi.system.domain.shop.Coupon;
import com.ruoyi.system.domain.shop.CouponPhoto;
import com.ruoyi.system.domain.shop.Shop;
import com.ruoyi.system.dto.CouponOperationExecution;
import com.ruoyi.system.dto.CouponPhotoOperationExecution;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.shop.CouponPhotoService;
import com.ruoyi.system.service.shop.CouponService;
import com.ruoyi.system.service.shop.ShopService;
import org.aspectj.weaver.loadtime.Aj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * coupon controller with shop owner
 *
 * @author zuoyangding
 */

@RestController
@RequestMapping("/coupon/manage")
public class CouponManagedController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(CouponManagedController.class);

    @Autowired
    CouponService couponService;

    @Autowired
    CouponPhotoService couponPhotoService;

    @Autowired
    ShopService shopService;

    /**
     * fetch coupon by shopId or time interval or all of them
     * @param couponBody
     * @return
     */
    @PostMapping("/list")
    @PreAuthorize("@ss.hasPermi('coupon:manage:list')")
    public AjaxResult fetchCoupon(@RequestBody CouponBody couponBody) {
        if (couponBody==null || couponBody.getShopId()==null || couponBody.getShopId()<1) {
            return AjaxResult.error(501, "Missing Required Arguments");
        }

        startPage();
        try {
            Coupon coupon = transferCouponBodyToCoupon(couponBody);
            CouponOperationExecution coe = couponService.getCouponList(coupon);
            if (coe.getState() == CouponStatusEnum.NO_COUPON.getState()) {
                return AjaxResult.success(CouponStatusEnum.NO_COUPON.getInfo());
            } else if (coe.getState() == CouponStatusEnum.SUCCESS.getState()) {
                return AjaxResult.success(coe.getCouponList());
            } else {
                return AjaxResult.error(coe.getStateInfo());
            }

        } catch (CustomException e) {
            log.debug("error when fetch coupon: {}", e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * fetch coupons by price interval
     * @param high
     * @param low
     * @return
     */
    @GetMapping("/list/byprice")
    @PreAuthorize("@ss.hasPermi('coupon:manage:list')")
    public AjaxResult fetchCouponByPrice(@RequestParam("high") String high, @RequestParam("low") String low) {
        Map<String, BigDecimal> priceInterval = new HashMap<>();
        if (high != null) {
            BigDecimal highPrice = new BigDecimal(high);
            priceInterval.put("high", highPrice);
        }
        if (low != null) {
            BigDecimal lowPrice = new BigDecimal(low);
            priceInterval.put("low", lowPrice);
        }

        try {
            CouponOperationExecution coe = couponService.getCouponListByPrice(priceInterval);
            if (coe.getState() == CouponStatusEnum.INNER_ERROR.getState()) {
                return AjaxResult.error(CouponStatusEnum.INNER_ERROR.getInfo());
            } else if (coe.getState() == CouponStatusEnum.NO_COUPON.getState()) {
                return AjaxResult.success(CouponStatusEnum.NO_COUPON.getInfo());
            } else {
                return AjaxResult.success(coe.getCouponList());
            }
        } catch (CustomException e) {
            return AjaxResult.error(e.getMessage());
        }

    }

    /**
     * add new coupon
     * @param couponBody
     * @return
     */
    @PostMapping("/register")
    @PreAuthorize("@ss.hasPermi('coupon:manage:add')")
    public AjaxResult couponRegister(@RequestBody CouponBody couponBody) {
        if (couponBody==null || couponBody.getShopId()==null
                || couponBody.getShopId()<1) {
            return AjaxResult.error(501, "Missing Required Arguments");
        }

        try {
            Coupon coupon = transferCouponBodyToCoupon(couponBody);
            CouponOperationExecution coe = couponService.addCoupon(coupon);
            if (coe.getState() == CouponStatusEnum.SUCCESS.getState()) {
                return AjaxResult.success();
            } else {
                return AjaxResult.error(CouponStatusEnum.INNER_ERROR.getInfo());
            }
        } catch (CustomException e) {
            log.debug("error in coupon registration: {}", e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * add coupon photo into db
     * @param photoAddBody
     * @return
     */
    @PostMapping("/couponphoto/addphoto")
    @PreAuthorize("@ss.hasPermi('coupon:manage:list')")
    public AjaxResult addCouponPhoto(@RequestBody PhotoAddBody photoAddBody) {
        if (photoAddBody==null || photoAddBody.getId()==null || photoAddBody.getId()<1
                || photoAddBody.getPhotoAddress()==null || photoAddBody.getPhotoAddress().size()<1) {
            return AjaxResult.error(501, "Missing Required Arguments");
        }

        List<CouponPhoto> couponPhotos = transferAddressToEntity(photoAddBody.getPhotoAddress(), photoAddBody.getId());

        try {
            if (couponPhotos.size() > 0) {
                CouponPhotoOperationExecution cpoe = couponPhotoService.addCouponPhotos(couponPhotos);
                if (cpoe.getState() == CouponPhotoStatusEnum.SUCCESS.getState()) {
                    return AjaxResult.success();
                }
            }
            return AjaxResult.error(CouponPhotoStatusEnum.INNER_ERROR.getInfo());
        } catch (CustomException e) {
            ShopImgFileUtil.deleteImgFile(photoAddBody.getPhotoAddress());
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * add coupon photo to server file storage
     * @param file
     * @return
     */
    @PostMapping("/couponphoto/savephoto")
    @PreAuthorize("@ss.hasPermi('coupon:manage:list')")
    public AjaxResult saveCouponPhoto(@RequestParam("file") MultipartFile file) {
        String baseDir = ShopImgFileUtil.getCouponBaseDir();
        log.debug("base dir {}", baseDir);
        try {
            String storagePath = ShopImgFileUtil.uploadImgFile(baseDir, file);
            log.debug("store dir {}", storagePath);
            return AjaxResult.success(storagePath);
        } catch (Exception e) {
            return AjaxResult.error(500, e.getMessage());
        }
    }

    /**
     * update coupon text part
     * @param couponBody
     * @return
     */
    @PutMapping("/update/general")
    @PreAuthorize("@ss.hasPermi('coupon:manage:edit')")
    public AjaxResult updateCoupon(@RequestBody CouponBody couponBody) {
        if (couponBody==null || couponBody.getCouponId()==null || couponBody.getCouponId()<1) {
            return AjaxResult.error(501, "Missing Required Arguments");
        }

        try {
            Coupon coupon = transferCouponBodyToCoupon(couponBody);
            CouponOperationExecution coe = couponService.updateCouponProfile(coupon);
            if (coe.getState() == CouponStatusEnum.SUCCESS.getState()) {
                return AjaxResult.success();
            } else {
                return AjaxResult.error(CouponStatusEnum.INNER_ERROR.getInfo());
            }
        } catch (CustomException e) {
            log.debug(e.getMessage());
            return AjaxResult.error(CouponStatusEnum.INNER_ERROR.getInfo());
        }
    }

    /**
     * update coupon photo
     * @param photoAddBody
     * @return
     */
    @PutMapping("/couponphoto/updatephoto")
    @PreAuthorize("@ss.hasPermi('coupon:manage:edit')")
    public AjaxResult updateCouponPhoto(@RequestBody PhotoAddBody photoAddBody) {
        if (photoAddBody==null || photoAddBody.getId()==null || photoAddBody.getId()<1
                || photoAddBody.getPhotoAddress()==null || photoAddBody.getPhotoAddress().size()<1) {
            return AjaxResult.error(501, "Miss Required Arguments");
        }

        try {
            // delete img from server
            CouponPhotoOperationExecution cpoe =
                    couponPhotoService.selectCouponPhotoListByCouponId(photoAddBody.getId());
            if (cpoe.getState() == CouponPhotoStatusEnum.INNER_ERROR.getState()) {
                return AjaxResult.error(CouponPhotoStatusEnum.INNER_ERROR.getInfo());
            }
            if (cpoe.getState() == CouponPhotoStatusEnum.SUCCESS.getState()) {
                List<String> photoAddress = transferEntityToAddress(cpoe.getCouponPhotos());
                ShopImgFileUtil.deleteImgFile(photoAddress);
            }

            // delete coupon photo from db
            CouponPhotoOperationExecution cpoe_delete =
                    couponPhotoService.deleteCouponPhotoByCouponId(photoAddBody.getId());

            if (cpoe_delete.getState()==CouponPhotoStatusEnum.SUCCESS.getState()
                    || cpoe_delete.getState()==CouponPhotoStatusEnum.NO_PHOTO.getState()) {
                // insert new coupon photo to db
                List<CouponPhoto> couponPhotos =
                        transferAddressToEntity(photoAddBody.getPhotoAddress(), photoAddBody.getId());
                CouponPhotoOperationExecution cpoe_insert = couponPhotoService.addCouponPhotos(couponPhotos);
                if (cpoe_insert.getState() == CouponPhotoStatusEnum.SUCCESS.getState()) {
                    return AjaxResult.success();
                } else {
                    log.debug("insert img to db error");
                    return AjaxResult.error(CouponPhotoStatusEnum.INNER_ERROR.getInfo());
                }
            } else {
                log.debug("delete img from db error");
                return AjaxResult.error(CouponPhotoStatusEnum.INNER_ERROR.getInfo());
            }
        } catch (CustomException e) {
            log.debug(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * delete coupon
     * @param couponId
     * @return
     */
    @DeleteMapping("/delete/{couponId}")
    @PreAuthorize("@ss.hasPermi('coupon:manage:remove')")
    public AjaxResult deleteCoupon(@PathVariable Long couponId) {
        if (couponId==null || couponId<1) {
            return AjaxResult.error(501, "Missing Required Arguments");
        }

        try {
            //delete coupon img from server file storage
            CouponPhotoOperationExecution cpoe = couponPhotoService.selectCouponPhotoListByCouponId(couponId);
            if (cpoe.getState() == CouponPhotoStatusEnum.INNER_ERROR.getState()) {
                return AjaxResult.error(CouponStatusEnum.INNER_ERROR.getInfo());
            }
            if (cpoe.getState() == CouponPhotoStatusEnum.SUCCESS.getState()) {
                List<CouponPhoto> couponPhotos = cpoe.getCouponPhotos();
                List<String> photoAddress = transferEntityToAddress(couponPhotos);
                ShopImgFileUtil.deleteImgFile(photoAddress);
            }

            // delete coupon img from db
            CouponPhotoOperationExecution cpoe_db = couponPhotoService.deleteCouponPhotoByCouponId(couponId);
            if (cpoe_db.getState()==CouponPhotoStatusEnum.SUCCESS.getState()
                    || cpoe_db.getState()==CouponPhotoStatusEnum.NO_PHOTO.getState()) {

                // delete coupon from db
                CouponOperationExecution coe = couponService.deleteCouponByCouponId(couponId);
                if (coe.getState()==CouponStatusEnum.SUCCESS.getState()) {
                    return AjaxResult.success();
                } else {
                    log.debug("error delete img from db when delete coupon");
                    return AjaxResult.error(CouponStatusEnum.INNER_ERROR.getInfo());
                }
            } else {
                log.debug("error delete coupon when delete coupon");
                return AjaxResult.error(CouponPhotoStatusEnum.INNER_ERROR.getInfo());
            }
        } catch (CustomException e) {
            log.debug("error in coupon delete: {}", e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * transfer coupon photo file storage address to db to coupon photo entity
     * @param photoAddress
     * @param couponId
     * @return
     */
    private List<CouponPhoto> transferAddressToEntity(List<String> photoAddress, Long couponId) {
        List<CouponPhoto> couponPhotos = new ArrayList<>();
        for (String photo : photoAddress) {
            CouponPhoto couponPhoto = new CouponPhoto();
            couponPhoto.setCouponId(couponId);
            String[] couponPhotoAddress = photo.split("profile");
            couponPhoto.setPhoto(couponPhotoAddress[1]);
            log.debug("coupon photo in photo adding {}", couponPhoto);
            couponPhotos.add(couponPhoto);
        }
        return couponPhotos;
    }

    /**
     * transfer coupon photo entity to coupon photo file storage address
     * @param couponPhotos
     * @return
     */
    private List<String> transferEntityToAddress(List<CouponPhoto> couponPhotos) {
        List<String> couponPhotoAddress = new ArrayList<>();
        for (CouponPhoto couponPhoto : couponPhotos) {
            couponPhotoAddress.add(couponPhoto.getPhoto());
        }
        return couponPhotoAddress;
    }

    /**
     * transfer http body to coupon entity
     * @param couponBody
     * @return
     * @throws RuntimeException
     */
    private Coupon transferCouponBodyToCoupon(CouponBody couponBody) throws RuntimeException{
        Coupon coupon = new Coupon();
        if (couponBody.getCouponId() != null) {
            coupon.setCouponId(couponBody.getCouponId());
        }
        if (couponBody.getShopId() != null) {
            Shop shop = new Shop();
            shop.setShopId(couponBody.getShopId());
            coupon.setShop(shop);
        }
        if (couponBody.getCouponCode() != null) {
            coupon.setCouponCode(couponBody.getCouponCode());
        }
        if (couponBody.getCouponDesc() != null) {
            coupon.setCouponDesc(couponBody.getCouponDesc());
        }
        if (couponBody.getCouponPrice() != null) {
            BigDecimal price = new BigDecimal(couponBody.getCouponPrice());
            BigDecimal div = new BigDecimal("100");
            price = price.divide(div, 2, RoundingMode.UNNECESSARY);
            coupon.setCouponPrice(price);
        }
        if (couponBody.getStartTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = null;
            try {
                startTime = sdf.parse(couponBody.getStartTime());
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage());
            }
            coupon.setStartTime(startTime);
        }
        if (couponBody.getEndTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date endTime = null;
            try {
                endTime = sdf.parse(couponBody.getEndTime());
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage());
            }
            coupon.setEndTime(endTime);
        }
        return coupon;
    }
}
