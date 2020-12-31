package com.ruoyi.email.mapper;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.core.domain.entity.Guest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class GuestMapperTest {
    @Autowired
    GuestMapper guestMapper;

    @Test
    public void createGuestTest() {
        Guest guest = new Guest();
        String guestEmail = "testEmail_4@testEmail.com";
        guest.setGuestEmail(guestEmail);
        int result = guestMapper.insertGuest(guest);
        assertEquals(1, result);
    }

    @Test
    public void selectGuestById() {
        Guest guest = guestMapper.selectGuestById(100L);
        assertNotNull(guest);
        System.out.println(guest);
    }

    @Test
    public void deleteGuestById() {
        int result = guestMapper.deleteGuestById(104L);
        assertEquals(1, result);
    }

    @Test
    public void deleteGuestByIds() {
        Long[] ids = {102L, 103L};
        int result = guestMapper.deleteGuestByIds(ids);
        assertEquals(2, result);
    }

    @Test
    public void deleteGuestByEmailAddrTest() {
        String emailAddress = "testEmail_1@testEmail.com";
        int result = guestMapper.deleteGuestByEmail(emailAddress);
        assertEquals(1, result);
    }
}
