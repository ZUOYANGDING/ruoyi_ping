package com.ruoyi.system.domain.shop;

import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * shop photo
 *
 * @author zuoyangding
 */

public class ShopPhoto {
    private static final long serialVersionUID = -2481214303681928043L;

    /** photo id **/
    @Excel(name = "shop photo Id", cellType = Excel.ColumnType.NUMERIC, prompt = "shop photo id")
    private Long photoId;

    /** photo storage place **/
    @Excel(name= "photo url", cellType = Excel.ColumnType.STRING, type = Excel.Type.IMPORT)
    private String photo;

    /** shop id **/
    @Excel(name="shop Id", cellType = Excel.ColumnType.NUMERIC, type = Excel.Type.EXPORT)
    private Long shopId;

    /** create time **/
    @Excel(name="create Time", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date createTime;

    /** last update time **/
    @Excel(name="create Time", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date updateTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("photoId", getPhotoId())
                .append("photoUrl", getPhoto())
                .append("shopId", getShopId())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
