package com.ruoyi.system.domain.shop;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * shop
 *
 * @author zuoyangding
 */


public class Shop {

    private static final long serialVersionUID = -7357190694957230978L;

    /** shop id **/
    @Excel(name="shop Id", cellType = Excel.ColumnType.NUMERIC, prompt = "shop Id")
    private Long shopId;

    /** shop name **/
    @Excel(name="shop name", cellType = Excel.ColumnType.STRING)
    private String shopName;

    /** shop description **/
    @Excel(name = "shop description", cellType = Excel.ColumnType.STRING)
    private String description;

    /** shop address **/
    @Excel(name = "shop address", cellType = Excel.ColumnType.STRING)
    private String address;

    /** shop status, -1 means ban, 0 means under check, 1 means pass check **/
    @Excel(name = "shop status", readConverterExp = "-1=ban, 0=check, 1=pass check")
    private String status;

    /** shop photos **/
    private List<ShopPhoto> shopPhotos;

    /** shop owner **/
    private SysUser sysUser;

    /** shop info create time **/
    @Excel(name="create Time", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date createTime;

    /** shop info last update time **/
    @Excel(name="update Time",width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date updateTime;

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

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ShopPhoto> getShopPhotos() {
        return shopPhotos;
    }

    public void setShopPhotos(List<ShopPhoto> shopPhotos) {
        this.shopPhotos = shopPhotos;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("shopId", getShopId())
                .append("shopStatus", getStatus())
                .append("shopName", getShopName())
                .append("shopDescription", getDescription())
                .append("shopAddress", getAddress())
                .append("shopPhotos", getShopPhotos())
                .append("shopCreateTime", getCreateTime())
                .append("shopUpdateTime", getUpdateTime())
                .append("shopOwnerId", getSysUser().getUserId())
                .append("shopOwnerNickName", getSysUser().getNickName())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
