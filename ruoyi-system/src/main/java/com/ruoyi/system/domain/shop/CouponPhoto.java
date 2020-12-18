package com.ruoyi.system.domain.shop;

import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * Coupon photo
 *
 * @author zuoyangding
 */


public class CouponPhoto {
    private static final long serialVersionUID = -5182197734456334595L;

    @Excel(name = "photo Id", cellType = Excel.ColumnType.NUMERIC, prompt = "photo Id")
    private Long photoId;

    @Excel(name = "photo url", cellType = Excel.ColumnType.STRING)
    private String photo;

    @Excel(name = "coupon id", cellType = Excel.ColumnType.NUMERIC)
    private Long couponId;

    @Excel(name="create Time",width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date createTime;

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

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Coupon Photo Id", getPhotoId())
                .append("Coupon Id", getCouponId())
                .append("Coupon Photo storage url", getPhoto())
                .append("Create Time", getCreateTime())
                .toString();
    }
}
