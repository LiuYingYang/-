package com.medusa.basemall.product.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_wlcompany")
public class Wlcompany {
    /**
     * ID
     */
    @Id
    @Column(name = "wl_id")
    private Integer wlId;

    /**
     * 物流公司名称
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     * 公司编码
     */
    @Column(name = "company_code")
    private String companyCode;

    /**
     * 获取ID
     *
     * @return wl_id - ID
     */
    public Integer getWlId() {
        return wlId;
    }

    /**
     * 设置ID
     *
     * @param wlId ID
     */
    public void setWlId(Integer wlId) {
        this.wlId = wlId;
    }

    /**
     * 获取物流公司名称
     *
     * @return company_name - 物流公司名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置物流公司名称
     *
     * @param companyName 物流公司名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 获取公司编码
     *
     * @return company_code - 公司编码
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * 设置公司编码
     *
     * @param companyCode 公司编码
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
}