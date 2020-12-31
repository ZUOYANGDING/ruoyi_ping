package com.ruoyi.email.mapper;

import com.ruoyi.common.core.domain.entity.Guest;

import java.util.List;

/**
 * guest mapper
 *
 * @author zuoyang
 */

public interface GuestMapper {
    /**
     * select guest by guest id
     * @param guestId
     * @return
     */
    public Guest selectGuestById(Long guestId);

    /**
     * insert new guest
     * @param guest
     * @return
     */
    public int insertGuest(Guest guest);

    /**
     * delete guest by id
     * @param guestId
     * @return
     */
    public int deleteGuestById(Long guestId);

    /**
     * delete guests by ids
     * @param guestIds
     * @return
     */
    public int deleteGuestByIds(Long[] guestIds);

    /**
     * delete guest by guest email
     * @param guestEmail
     * @return
     */
    public int deleteGuestByEmail(String guestEmail);
}
