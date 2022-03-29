package com.jcohy.framework.starter.sms.request.sign;

import com.jcohy.framework.starter.sms.SmsAction;
import com.jcohy.framework.starter.sms.request.SmsSignRequest;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/29/22:16:53
 * @since 2022.0.1
 */
public class TencentSmsSignRequest extends SmsSignRequest {

    private Long signType;

    private Long documentType;

    private Long international;

    private Long signPurpose;

    private String proofImage;

    private String commissionImage;

    private Long limit;

    private Long offset;

    public TencentSmsSignRequest(SmsAction action) {
        super(action);
    }

    public Long getSignType() {
        return this.signType;
    }

    public TencentSmsSignRequest signType(Long signType) {
        this.signType = signType;
        return this;
    }

    public Long getDocumentType() {
        return this.documentType;
    }

    public TencentSmsSignRequest documentType(Long documentType) {
        this.documentType = documentType;
        return this;
    }

    public Long getInternational() {
        return this.international;
    }

    public TencentSmsSignRequest international(Long international) {
        this.international = international;
        return this;
    }

    public Long getSignPurpose() {
        return this.signPurpose;
    }

    public TencentSmsSignRequest signPurpose(Long signPurpose) {
        this.signPurpose = signPurpose;
        return this;
    }

    public String getProofImage() {
        return this.proofImage;
    }

    public TencentSmsSignRequest proofImage(String proofImage) {
        this.proofImage = proofImage;
        return this;
    }

    public String getCommissionImage() {
        return this.commissionImage;
    }

    public TencentSmsSignRequest commissionImage(String commissionImage) {
        this.commissionImage = commissionImage;
        return this;
    }

    public Long getLimit() {
        return this.limit;
    }

    public TencentSmsSignRequest limit(Long limit) {
        this.limit = limit;
        return this;
    }

    public Long getOffset() {
        return this.offset;
    }

    public TencentSmsSignRequest offset(Long offset) {
        this.offset = offset;
        return this;
    }

}
