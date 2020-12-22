package com.ruoyi.system.mapper.shop;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.domain.shop.CouponPhoto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class CouponPhotoMapperTest {
    @Autowired
    CouponPhotoMapper couponPhotoMapper;

    @Test
    public void insertCouponPhotoTest() {
        CouponPhoto couponPhoto = new CouponPhoto();
        Long couponId = 100L;
        couponPhoto.setCouponId(couponId);
        couponPhoto.setPhoto("test url 1");
        int result = couponPhotoMapper.insertCouponPhoto(couponPhoto);
        assertEquals(result, 1);
    }

    @Test
    public void getCouponPhotoByIdTest() {
        Long photoId = 100L;
        CouponPhoto couponPhoto = couponPhotoMapper.getCouponPhotoById(photoId);
        assertNotNull(couponPhoto);
        System.out.println(couponPhoto);
    }

    @Test
    public void batchInsertCouponPhotoTest() {
        List<CouponPhoto> photos = new ArrayList<>();
        int count = 10;
        for (int i=0; i<3; i++) {
            CouponPhoto couponPhoto = new CouponPhoto();
            couponPhoto.setCouponId(101L);
            couponPhoto.setPhoto("test/couponphoto/url/" + count);
            count++;
            photos.add(couponPhoto);
        }
        int result = couponPhotoMapper.batchInsertCouponPhoto(photos);
        assertEquals(3, result);
    }

    @Test
    public void getCouponPhotoByCouponIdTest() {
        Long couponId = 100L;
        List<CouponPhoto> couponPhotos = couponPhotoMapper.getCouponPhotoByCouponId(couponId);
        assertNotNull(couponPhotos);
        couponPhotos.stream().forEach(couponPhoto -> {
            System.out.println(couponPhoto);
        });
    }

    @Test
    public void deleteCouponPhotoByIdTest() {
        Long photoId = 109L;
        int result = couponPhotoMapper.deleteCouponPhotoById(photoId);
        assertEquals(result, 1);
    }

    @Test
    public void deleteCouponPhotoByIdsTest() {
        Long[] photoIds = {107L, 108L};
        int result = couponPhotoMapper.deleteCouponPhotoByIds(photoIds);
        assertEquals(result, 2);

    }

    @Test
    public void deleteCouponPhotoByCouponIdTest() {
        Long couponId = 101L;
        int result = couponPhotoMapper.deleteCouponPhotoByCouponId(couponId);
        assertEquals(3, result);
    }
}