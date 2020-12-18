package com.ruoyi.system.mapper.shop;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.domain.shop.ShopPhoto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class ShopPhotoMapperTest {

    @Autowired
    ShopPhotoMapper shopPhotoMapper;

    @Test
    public void insertShopPhotoTest() {
        ShopPhoto shopPhoto = new ShopPhoto();
        shopPhoto.setPhoto("test/photo/url/3");
        shopPhoto.setShopId(100L);
        int result = shopPhotoMapper.insertShopPhoto(shopPhoto);
        assertEquals(1, result);
    }

    @Test
    public void getShopPhotoListByShopId() {
        Long shopId = 100L;
        List<ShopPhoto> photoList = shopPhotoMapper.getShopPhotoListByShopId(shopId);
        assertNotNull(photoList);
        photoList.stream().forEach(shopPhoto -> {
            System.out.println(shopPhoto);
        });
    }

    @Test
    public void batchInsertShopPhotoTest() {
        Long shopId = 101L;
        int count = 4;
        List<ShopPhoto> photoList = new ArrayList<>();
        for (int i=0; i<3; i++) {
            ShopPhoto shopPhoto = new ShopPhoto();
            shopPhoto.setPhoto("test/photo/url/" + count);
            shopPhoto.setShopId(shopId);
            photoList.add(shopPhoto);
            count++;
        }
        int result = shopPhotoMapper.batchInsertShopPhoto(photoList);
        assertEquals(result, 3);
    }

}