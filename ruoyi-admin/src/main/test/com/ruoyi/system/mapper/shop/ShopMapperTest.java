package com.ruoyi.system.mapper.shop;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.domain.shop.Shop;
import com.ruoyi.system.mapper.SysUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class ShopMapperTest {
    @Autowired
    ShopMapper shopMapper;
    @Autowired
    SysUserMapper sysUserMapper;

    @Test
    public void insertShopTest() {
        Shop shop = new Shop();
        SysUser sysUser = sysUserMapper.selectUserById(1L);

        shop.setStatus("1");
        shop.setShopName("test shop 4");
        shop.setAddress("test shop address 4");
        shop.setSysUser(sysUser);
        shop.setDescription("test shop desc 4");

        int result = shopMapper.insertShop(shop);
        assertEquals(1, result);
    }

    @Test
    public void selectShopByIdTest() {
        Long shopId = 101L;
        Shop shop = shopMapper.selectShopByShopId(shopId);
        assertNotNull(shop);
        System.out.println(shop);
    }

    @Test
    public void selectShopByShopNameTest() {
        String shopName = "test shop 1";
        Shop result = shopMapper.selectShopByShopName(shopName);
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    public void selectShopListTest() {
        SysUser sysUser = sysUserMapper.selectUserById(1L);
        Shop shop = new Shop();
        shop.setSysUser(sysUser);
        List<Shop> shops = shopMapper.selectShopList(shop);
        shops.stream().forEach(shop1 -> {
            System.out.println(shop1);
            System.out.println("============================================");
        });
    }

    @Test
    public void checkShopNameUniqueTest() {
        String shopName = "test shop 3";
        int result = shopMapper.checkShopNameUnique(shopName);
        System.out.println(result);
    }

    @Test
    public void updateShopTest() {
        Shop shop = new Shop();
        shop.setShopId(103L);
        shop.setShopName("test shop 3 update");
        shop.setAddress("test shop 3 update");
        shop.setStatus("0");
        shop.setDescription("test shop 3 description update");

        int result = shopMapper.updateShop(shop);
        assertEquals(result, 1);
    }

    @Test
    public void deleteShopByShopId() {
        Long shopId = 103L;
        int result = shopMapper.deleteShopByShopId(shopId);
        assertEquals(1, result);
    }

    @Test
    public void deleteShopByShopIdsTest(){
        Long[] shopIds = {104L, 105L};
        int result = shopMapper.deleteShopByShopIds(shopIds);
        assertEquals(2, result);
    }

}