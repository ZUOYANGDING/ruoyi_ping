package com.ruoyi.common.core.domain.model;

import java.util.List;

/**
 * add photo entity
 *
 * @author ruoyi
 */
public class PhotoAddBody {
    private Long id;
    private List<String> photoAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getPhotoAddress() {
        return photoAddress;
    }

    public void setPhotoAddress(List<String> photoAddress) {
        this.photoAddress = photoAddress;
    }
}
