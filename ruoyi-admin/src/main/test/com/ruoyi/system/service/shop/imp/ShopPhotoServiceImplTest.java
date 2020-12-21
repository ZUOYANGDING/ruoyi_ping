package com.ruoyi.system.service.shop.imp;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.enums.shop.ShopPhotoStatusEnum;
import com.ruoyi.system.domain.shop.ShopPhoto;
import com.ruoyi.system.dto.ShopPhotoOperationExecution;
import com.ruoyi.system.service.shop.ShopPhotoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class ShopPhotoServiceImplTest {

    @Autowired
    ShopPhotoService shopPhotoService;

    @Test
    public void selectShopPhotoByIdTest() {
        Long photoId = 100L;
        ShopPhotoOperationExecution soe = shopPhotoService.selectShopPhotoById(photoId);
        assertNotNull(soe);
        assertEquals(soe.getState(), ShopPhotoStatusEnum.SUCCESS.getState());
        System.out.println(soe.getShopPhoto());
    }

    @Test
    public void selectShopPhotoListByShopIdTest() {
        Long shopId = 100L;
        ShopPhotoOperationExecution soe = shopPhotoService.selectShopPhotoListByShopId(shopId);
        assertEquals(soe.getState(), ShopPhotoStatusEnum.SUCCESS.getState());
        soe.getShopPhotos().stream().forEach(shopPhoto -> {
            System.out.println(shopPhoto);
        });
    }


    @Test
    public void addShopPhotoTest() {
        ShopPhoto shopPhoto = new ShopPhoto();
        shopPhoto.setShopId(102L);
        shopPhoto.setPhoto("test/photo/url/7");
        ShopPhotoOperationExecution soe = shopPhotoService.addShopPhoto(shopPhoto);
        assertEquals(soe.getState(), ShopPhotoStatusEnum.SUCCESS.getState());
    }

    @Test
    public void addShopPhotosTest() {
        int count = 8;
        List<ShopPhoto> shopPhotos = new ArrayList<>();
        for (int i=0; i<3; i++) {
            ShopPhoto shopPhoto = new ShopPhoto();
            shopPhoto.setShopId(102L);
            shopPhoto.setPhoto("test/photo/url/" + count);
            count++;
            shopPhotos.add(shopPhoto);
        }
        ShopPhotoOperationExecution soe = shopPhotoService.addShopPhotos(shopPhotos);
        assertEquals(soe.getState(), ShopPhotoStatusEnum.SUCCESS.getState());
    }

    @Test
    public void deleteShopPhotoByIdTest() {
        Long photoId = 127L;
        ShopPhotoOperationExecution soe = shopPhotoService.deleteShopPhotoById(photoId);
        assertNotNull(soe);
        assertEquals(soe.getState(), ShopPhotoStatusEnum.SUCCESS.getState());
    }

    @Test
    public void deleteShopPhotoByIdsTest() {
        Long[] photoIds = {128L, 129L, 130L};
        ShopPhotoOperationExecution soe = shopPhotoService.deleteShopPhotoByIds(photoIds);
        assertNotNull(soe);
        assertEquals(soe.getState(), ShopPhotoStatusEnum.SUCCESS.getState());
    }
}