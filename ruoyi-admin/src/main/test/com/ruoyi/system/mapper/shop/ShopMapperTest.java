package com.ruoyi.system.mapper.shop;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.shop.Shop;
import com.ruoyi.system.mapper.SysUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        SysUser sysUser = sysUserMapper.selectUserById(2L);

        shop.setShopName("test shop 2");
        shop.setAddress("test shop address 2");
        shop.setSysUser(sysUser);
        shop.setDescription("test shop desc 2");

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

}