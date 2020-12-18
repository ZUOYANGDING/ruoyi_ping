package com.ruoyi.system.domain.shop;

import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * coupon
 *
 * @author zuoyangding
 */

public class Coupon {
    private static final long serialVersionUID = 642905296114156131L;

    /** coupon id**/
    @Excel(name = "coupon id", cellType = Excel.ColumnType.NUMERIC, prompt = "coupon id")
    private Long couponId;

    /** coupon price **/
    @Excel(name = "coupon price", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal couponPrice;

    /** coupon description**/
    @Excel(name = "coupon description", cellType = Excel.ColumnType.STRING)
    private String couponDesc;

    /** coupon description **/
    @Excel(name = "coupon code", cellType = Excel.ColumnType.STRING)
    private String couponCode;

    /** coupon start time **/
    @Excel(name="start Time", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date startTime;

    /** coupon end  time **/
    @Excel(name="end Time",width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date endTime;

    private Shop shop;

    private List<CouponPhoto> photoList;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<CouponPhoto> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<CouponPhoto> photoList) {
        this.photoList = photoList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Coupon Id", getCouponId())
                .append("Shop Id", getShop().getShopId())
                .append("Shop name", getShop().getShopName())
                .append("Coupon code", getCouponCode())
                .append("Coupon Description", getCouponDesc())
                .append("Coupon price", getCouponPrice())
                .append("Start Time", getStartTime())
                .append("End time", getEndTime())
                .append("Coupon photos", getPhotoList())
                .toString();
    }
}
