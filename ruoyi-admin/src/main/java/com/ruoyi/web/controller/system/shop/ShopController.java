package com.ruoyi.web.controller.system.shop;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.PhotoAddBody;
import com.ruoyi.common.enums.shop.ShopPhotoStatusEnum;
import com.ruoyi.common.enums.shop.ShopStatesEnum;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.file.shop.ShopImgFileUtil;
import com.ruoyi.system.domain.shop.Shop;
import com.ruoyi.system.domain.shop.ShopPhoto;
import com.ruoyi.system.dto.ShopOperationExecution;
import com.ruoyi.system.dto.ShopPhotoOperationExecution;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.shop.ShopPhotoService;
import com.ruoyi.system.service.shop.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * shop controller with user auth
 *
 * @author zuoyangding
 */
@RestController
@RequestMapping("/shop/manage/")
public class ShopController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(ShopController.class);
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopPhotoService shopPhotoService;

    /**
     * filter shop by filters but with necessary userId
     * @param shop
     * @return
     */
    @PostMapping("/list")
    public AjaxResult fetchShopList(@RequestBody Shop shop) {
        //TODO auth for currentUser
        SysUser currentUser = userService.selectUserByUserName("admin");
        shop.setSysUser(currentUser);

        startPage();
        try {
            ShopOperationExecution soe = shopService.selectShopList(shop);
            if (soe.getState() == ShopStatesEnum.NO_SHOP.getState()) {
                return AjaxResult.success(ShopStatesEnum.NO_SHOP.getInfo());
            } else if (soe.getState() == ShopStatesEnum.SUCCESS.getState()){
                return AjaxResult.success(getDataTable(soe.getShopList()));
            } else {
                return AjaxResult.error(soe.getStateInfo());
            }
        } catch (CustomException e) {
            log.debug("error whe fetch shop: {}", e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * add new shop
     * code:  501 missing required arguments
     * @param shop
     * @return
     */
    @PostMapping("/register")
    public AjaxResult shopRegister(@RequestBody Shop shop) {
        if (shop==null || shop.getShopName()==null) {
            return AjaxResult.error(501, "Missing Required arguments");
        }

        //TODO auth for currentUser
        SysUser currentUser = userService.selectUserByUserName("admin");
        shop.setSysUser(currentUser);

        try {
            ShopOperationExecution soe = shopService.addShop(shop);
            if (soe.getState() == ShopStatesEnum.SUCCESS.getState()) {
                return AjaxResult.success();
            } else {
                return AjaxResult.error(ShopStatesEnum.INNER_ERROR.getInfo());
            }
        } catch (CustomException e) {
            log.debug("error in shop registration: {}", e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * add photo into db
     * @param photoAddBody
     * @return
     */
    @PostMapping("/shopphoto/addphoto")
    public AjaxResult addShopPhoto(@RequestBody PhotoAddBody photoAddBody) {
        if (photoAddBody==null || photoAddBody.getShopId()==null || photoAddBody.getShopId()<1 ||
                photoAddBody.getPhotoAddress()==null || photoAddBody.getPhotoAddress().size()<1) {
            return AjaxResult.error(501, "Missing Required arguments");
        }
        // create shop photo list
        List<ShopPhoto> shopPhotos = transferAddressToEntity(photoAddBody.getPhotoAddress(), photoAddBody.getShopId());

        try {
            if (shopPhotos.size()>0) {
                ShopPhotoOperationExecution spoe = shopPhotoService.addShopPhotos(shopPhotos);
                if (spoe.getState() == ShopPhotoStatusEnum.SUCCESS.getState()) {
                    return AjaxResult.success();
                }
            }
            return AjaxResult.error(ShopStatesEnum.INNER_ERROR.getInfo());
        } catch (CustomException e) {
            ShopImgFileUtil.deleteImgFile(photoAddBody.getPhotoAddress());
            return AjaxResult.error(e.getMessage());
        }
    }


    /**
     * upload photo to server
     * storage relative path after /profile/
     * @param file
     * @return
     */
    @PostMapping("/shopphoto/savephoto")
    public AjaxResult saveShopPhoto(@RequestParam("file") MultipartFile file) {
        String baseDir = ShopImgFileUtil.getShopBaseDir();
        log.debug("base dir {}", baseDir);
        try {
            String storagePath = ShopImgFileUtil.uploadImgFile(baseDir, file);
            log.debug("store dir {}", storagePath);
            return AjaxResult.success(storagePath);
        } catch (Exception e) {
            return AjaxResult.error(500, e.getMessage());
        }
    }

    @PostMapping("/update/general")
    public AjaxResult updateShop(@RequestBody Shop shop) {
        if (shop==null || shop.getShopId()==null || shop.getShopId()<1) {
            return AjaxResult.error(501, "Missing Required arguments");
        }

        try {
            ShopOperationExecution soe = shopService.updateShopProfile(shop);
            if (soe.getState() == ShopStatesEnum.SUCCESS.getState()) {
                return AjaxResult.success();
            } else {
                return AjaxResult.error(ShopStatesEnum.INNER_ERROR.getInfo());
            }
        } catch (CustomException e) {
            log.debug(e.getMessage());
            return AjaxResult.error(ShopStatesEnum.INNER_ERROR.getInfo());
        }
    }

    @PostMapping("/shopphoto/updatephoto")
    public AjaxResult updateShopPhoto(@RequestBody PhotoAddBody photoAddBody) {
        if (photoAddBody==null || photoAddBody.getShopId()==null || photoAddBody.getShopId()<1
                || photoAddBody.getPhotoAddress()==null || photoAddBody.getPhotoAddress().size()<1) {
            return AjaxResult.error(501, "Missing Required arguments");
        }

        try {
            // delete img from server
            ShopPhotoOperationExecution spoe = shopPhotoService.selectShopPhotoListByShopId(photoAddBody.getShopId());
            if (spoe.getState() == ShopPhotoStatusEnum.INNER_ERROR.getState()) {
                return AjaxResult.error(ShopPhotoStatusEnum.INNER_ERROR.getInfo());
            }
            List<String> photoAddress = transferEntityToAddress(spoe.getShopPhotos());
            ShopImgFileUtil.deleteImgFile(photoAddress);

            // delete shop photo from db
            ShopPhotoOperationExecution spoe_delete =
                    shopPhotoService.deleteShopPhotoByShopId(photoAddBody.getShopId());

            if (spoe_delete.getState()==ShopPhotoStatusEnum.SUCCESS.getState()
                    || spoe_delete.getState()==ShopPhotoStatusEnum.NO_PHOTO.getState()) {
                // insert new shop photos to db
                List<ShopPhoto> shopPhotos =
                        transferAddressToEntity(photoAddBody.getPhotoAddress(), photoAddBody.getShopId());
                ShopPhotoOperationExecution spoe_insert = shopPhotoService.addShopPhotos(shopPhotos);
                if (spoe_insert.getState() == ShopPhotoStatusEnum.SUCCESS.getState()) {
                    return AjaxResult.success();
                } else {
                    return AjaxResult.error(ShopPhotoStatusEnum.INNER_ERROR.getInfo());
                }
            } else {
                log.debug("delete img from db error");
                return AjaxResult.error(ShopPhotoStatusEnum.INNER_ERROR.getInfo());
            }

        } catch (CustomException e) {
            log.debug(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }


    private List<ShopPhoto> transferAddressToEntity(List<String> photoAddress, Long shopId) {
        List<ShopPhoto> shopPhotos = new ArrayList<>();
        for (String photo : photoAddress) {
            ShopPhoto shopPhoto = new ShopPhoto();
            shopPhoto.setShopId(shopId);
            String[] shopPhotoAddress = photo.split("profile");
            shopPhoto.setPhoto(shopPhotoAddress[1]);
            log.debug("shop photo in photo adding: {}", shopPhoto);
            shopPhotos.add(shopPhoto);
        }
        return shopPhotos;
    }

    private List<String> transferEntityToAddress(List<ShopPhoto> photos) {
        List<String> shopAddress = new ArrayList<>();
        for (ShopPhoto photo: photos) {
            shopAddress.add(photo.getPhoto());
        }
        return shopAddress;
    }
}
