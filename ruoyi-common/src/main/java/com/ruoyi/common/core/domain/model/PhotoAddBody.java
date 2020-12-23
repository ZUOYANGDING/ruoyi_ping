package com.ruoyi.common.core.domain.model;

import java.util.List;

/**
 * add photo entity
 *
 * @author ruoyi
 */
public class PhotoAddBody {
    private Long shopId;
    private List<String> photoAddress;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public List<String> getPhotoAddress() {
        return photoAddress;
    }

    public void setPhotoAddress(List<String> photoAddress) {
        this.photoAddress = photoAddress;
    }
}
