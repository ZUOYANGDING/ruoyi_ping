package com.ruoyi.common.core.domain.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * guest info
 *
 * @author zuoyang
 */

public class Guest {
    private static final long serialVersionUID = -1148587605568083975L;

    private Long guestId;

    private String guestEmail;

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("GuestId", getGuestId())
                .append("GuestEmail", getGuestEmail())
                .toString();
    }
}
