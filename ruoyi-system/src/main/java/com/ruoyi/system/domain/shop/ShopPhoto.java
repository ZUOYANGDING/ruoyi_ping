package com.ruoyi.system.domain.shop;

import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ShopPhoto {
    private static final long serialVersionUID = -2481214303681928043L;

    @Excel(name = "shop photo Id", cellType = Excel.ColumnType.NUMERIC, prompt = "shop photo id")
    private Long photoId;

    @Excel(name= "photo url", cellType = Excel.ColumnType.STRING, type = Excel.Type.IMPORT)
    private String photo;

    @Excel(name="shop Id", cellType = Excel.ColumnType.NUMERIC, type = Excel.Type.EXPORT)
    private Long shopId;

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("photoId", getPhotoId())
                .append("photoUrl", getPhoto())
                .append("shopId", getShopId())
                .toString();
    }
}
