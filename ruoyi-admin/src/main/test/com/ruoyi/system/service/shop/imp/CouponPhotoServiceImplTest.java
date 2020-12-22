package com.ruoyi.system.service.shop.imp;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.enums.shop.CouponPhotoStatusEnum;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.domain.shop.CouponPhoto;
import com.ruoyi.system.dto.CouponPhotoOperationExecution;
import com.ruoyi.system.service.shop.CouponPhotoService;
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
public class CouponPhotoServiceImplTest {
    @Autowired
    CouponPhotoService couponPhotoService;

    @Test
    public void selectCouponPhotoByIdTest() {
        Long photoId = 103L;
        CouponPhotoOperationExecution coe = couponPhotoService.selectCouponPhotoById(photoId);
        assertNotNull(coe);
        assertEquals(coe.getState(), CouponPhotoStatusEnum.SUCCESS.getState());
        System.out.println(coe.getCouponPhoto());
    }

    @Test
    public void selectCouponPhotoListByCouponIdTest() {
        Long couponId = 101L;
        CouponPhotoOperationExecution coe = couponPhotoService.selectCouponPhotoListByCouponId(couponId);
        assertNotNull(coe);
        assertEquals(coe.getState(), CouponPhotoStatusEnum.SUCCESS.getState());
        coe.getCouponPhotos().stream().forEach(couponPhoto -> {
            System.out.println(couponPhoto);
        });
    }

    @Test
    public void addCouponPhotoTest() {
        CouponPhoto couponPhoto = new CouponPhoto();
        couponPhoto.setCouponId(101L);
        couponPhoto.setPhoto("test/coupon/url/13");
        couponPhoto.setCreateTime(new Date());
        CouponPhotoOperationExecution coe = couponPhotoService.addCouponPhoto(couponPhoto);
        assertNotNull(coe);
        assertEquals(coe.getState(), CouponPhotoStatusEnum.SUCCESS.getState());
    }

    @Test
    public void addCouponPhotosTest() {
        List<CouponPhoto> couponPhotos = new ArrayList<>();
        int count = 17;
        for (int i=0; i<3; i++) {
            CouponPhoto couponPhoto = new CouponPhoto();
            couponPhoto.setCouponId(108L);
            couponPhoto.setPhoto("test/couponphoto/url/" + count);
            count++;
            couponPhotos.add(couponPhoto);
        }
        CouponPhotoOperationExecution coe = couponPhotoService.addCouponPhotos(couponPhotos);
        assertNotNull(coe);
        assertEquals(coe.getState(), CouponPhotoStatusEnum.SUCCESS.getState());
    }

    @Test
    public void deleteCouponPhotoByIdTest() {
        Long photoId = 128L;
        CouponPhotoOperationExecution coe = couponPhotoService.deleteCouponPhotoById(photoId);
        assertNotNull(coe);
        assertEquals(coe.getState(), CouponPhotoStatusEnum.SUCCESS.getState());
    }

    @Test
    public void deleteCouponPhotoByIdsTest() {
        Long[] photoIds = {126L, 127L};
        CouponPhotoOperationExecution coe = couponPhotoService.deleteCouponPhotoByIds(photoIds);
        assertNotNull(coe);
        assertEquals(coe.getState(), CouponPhotoStatusEnum.SUCCESS.getState());
    }
}