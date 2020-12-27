package com.ruoyi.system.service.shop.imp;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.shop.ShopStatesEnum;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.domain.shop.Shop;
import com.ruoyi.system.dto.ShopOperationExecution;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.shop.ShopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class ShopServiceImplTest {
    @Autowired
    ShopService shopService;
    @Autowired
    ISysUserService iSysUserService;

    @Test
    public void selectShopListTest() {
        SysUser sysUser = iSysUserService.selectUserById(1L);
        Shop shop = new Shop();
        shop.setSysUser(sysUser);
        shop.setStatus("0");
        ShopOperationExecution soe = shopService.selectShopList(shop);
        assertNotNull(soe);
        assertEquals(soe.getState(), ShopStatesEnum.SUCCESS.getState());
        soe.getShopList().stream().forEach(shop1 -> {
            System.out.println(shop1);
        });
    }

    @Test
    public void selectShopByIdTest() {
        Long shopId = 100L;
        String status = "0";
        ShopOperationExecution soe = shopService.selectShopById(shopId, status);
        assertNotNull(soe);
        assertEquals(soe.getState(), ShopStatesEnum.SUCCESS.getState());
        System.out.println(soe.getShop());
        status = "1";
        soe = shopService.selectShopById(shopId, status);
        assertEquals(soe.getState(), ShopStatesEnum.NO_SHOP.getState());
    }

    @Test
    public void addShopTest() {
        SysUser sysUser = iSysUserService.selectUserById(2L);
        assertNotNull(sysUser);

        Shop shop = new Shop();
        shop.setShopName("test shop 5");
        shop.setDescription("test shop desc 5");
        shop.setAddress("test shop addresss 5");
        // test status change interception
        shop.setStatus("1");
        shop.setSysUser(sysUser);
        ShopOperationExecution soe = shopService.addShop(shop);
        assertNotNull(soe);
        assertEquals(soe.getState(), ShopStatesEnum.SUCCESS.getState());
    }

    @Test
    public void updateShopProfileTest() {
        Shop shop = new Shop();
        shop.setStatus("1");
        shop.setShopName("test shop updates 4");
        shop.setShopId(106L);
        ShopOperationExecution soe = shopService.updateShopProfile(shop);
        assertNotNull(soe);
        assertEquals(soe.getState(), ShopStatesEnum.SUCCESS.getState());
    }

    @Test
    public void updateShopStatusTest() {
        Shop shop = new Shop();
        shop.setShopId(102L);
        shop.setStatus("0");
        ShopOperationExecution soe = shopService.updateShopStates(shop);
        assertNotNull(soe);
        assertEquals(soe.getState(), ShopStatesEnum.SUCCESS.getState());
    }

    @Test
    public void deleteShopByIdTest() {
        Long shopId = 109L;
        ShopOperationExecution soe = shopService.deleteShopById(shopId);
        assertNotNull(soe);
        assertEquals(soe.getState(), ShopStatesEnum.SUCCESS.getState());
    }

    @Test
    public void deleteShopByIdsTest() {
        Long[] shopIds = {107L, 108L};
        ShopOperationExecution soe = shopService.deleteShopByIds(shopIds);
        assertNotNull(soe);
        assertEquals(soe.getState(), ShopStatesEnum.SUCCESS.getState());
    }

}