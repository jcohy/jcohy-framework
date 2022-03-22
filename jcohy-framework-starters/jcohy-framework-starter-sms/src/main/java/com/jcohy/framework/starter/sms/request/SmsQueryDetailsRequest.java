package com.jcohy.framework.starter.sms.request;

import java.time.LocalDate;
import java.util.Objects;

import org.apache.commons.lang3.Validate;

import com.jcohy.framework.starter.sms.SmsAction;
import com.jcohy.framework.utils.DateTimeUtils;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/16/22:18:15
 * @since 2022.0.1
 */
public class SmsQueryDetailsRequest extends SmsRequest {

    /**
     * 手机号.
     */
    private String phone;

    /**
     * 回执ID.
     */
    private String bizId;

    /**
     * 开始时间.
     *
     * 格式为：yyyyMMdd
     */
    private String startDate;

    /**
     * 短信发送范围. 1.国内短信发送记录 2.国际/港澳台短信发送记录
     */
    private Integer isGlobe;

    /**
     * 发送时间.
     *
     * 格式为：yyyyMMdd
     */
    private String sendDate;

    /**
     * 结束时间.
     *
     * 格式为：yyyyMMdd
     */
    private String endDate;

    /**
     * 页码.
     */
    private Long pageIndex;

    /**
     * 页数.
     */
    private Long pageSize;

    public SmsQueryDetailsRequest(SmsAction action) {
        super(action);
        Validate.notNull(action, "action 不能为空 !");
    }

    public String getPhone() {
        return this.phone;
    }

    public SmsQueryDetailsRequest phone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getBizId() {
        return this.bizId;
    }

    public SmsQueryDetailsRequest bizId(String bizId) {
        this.bizId = bizId;
        return this;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public SmsQueryDetailsRequest startDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public SmsQueryDetailsRequest startDate(LocalDate startDate) {
        this.startDate = DateTimeUtils.format(startDate, "yyyyMMdd");
        return this;
    }

    public Integer getIsGlobe() {
        return this.isGlobe;
    }

    public SmsQueryDetailsRequest globe(Integer isGlobe) {
        this.isGlobe = isGlobe;
        return this;
    }

    public String getSendDate() {
        return this.sendDate;
    }

    public SmsQueryDetailsRequest sendDate(String sendDate) {
        this.sendDate = sendDate;
        return this;
    }

    public SmsQueryDetailsRequest sendDate(LocalDate sendDate) {
        this.sendDate = DateTimeUtils.format(sendDate, "yyyyMMdd");
        return this;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public SmsQueryDetailsRequest endDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public SmsQueryDetailsRequest endDate(LocalDate endDate) {
        this.endDate = DateTimeUtils.format(endDate, "yyyyMMdd");
        return this;
    }

    public Long getPageIndex() {
        return this.pageIndex;
    }

    public SmsQueryDetailsRequest pageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public Long getPageSize() {
        return this.pageSize;
    }

    public SmsQueryDetailsRequest pageSize(Long pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SmsQueryDetailsRequest that = (SmsQueryDetailsRequest) o;
        return Objects.equals(getPhone(), that.getPhone()) && Objects.equals(getBizId(), that.getBizId())
                && Objects.equals(getStartDate(), that.getStartDate())
                && Objects.equals(getIsGlobe(), that.getIsGlobe()) && Objects.equals(getSendDate(), that.getSendDate())
                && Objects.equals(getEndDate(), that.getEndDate())
                && Objects.equals(getPageIndex(), that.getPageIndex())
                && Objects.equals(getPageSize(), that.getPageSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPhone(), getBizId(), getStartDate(), getIsGlobe(), getSendDate(),
                getEndDate(), getPageIndex(), getPageSize());
    }

    @Override
    public String toString() {
        return "SmsQueryDetailsRequest{" + "phone='" + this.phone + '\'' + ", bizId='" + this.bizId + '\''
                + ", startDate='" + this.startDate + '\'' + ", isGlobe=" + this.isGlobe + ", sendDate='" + this.sendDate
                + '\'' + ", endDate='" + this.endDate + '\'' + ", pageIndex=" + this.pageIndex + ", pageSize="
                + this.pageSize + '}';
    }

}
