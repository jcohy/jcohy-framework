package com.jcohy.framework.starter.sms.request;

import com.jcohy.framework.starter.sms.SmsAction;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/16/22:18:25
 * @since 2022.0.1
 */
public class SmsSignRequest extends SmsRequest {

    private String signName;

    private Integer signSource;

    private String remark;

    private List<SignFile> signFile;

    private Integer pageIndex;

    private Integer pageSize;

    public SmsSignRequest(SmsAction action) {
        super(action);
        Validate.notNull(action,"action 不能为空 !");
    }

    public String getSignName() {
        return this.signName;
    }

    public SmsSignRequest signName(String signName) {
        this.signName = signName;
        return this;
    }

    public Integer getSignSource() {
        return this.signSource;
    }

    public SmsSignRequest signSource(Integer signSource) {
        this.signSource = signSource;
        return this;
    }

    public String getRemark() {
        return this.remark;
    }

    public SmsSignRequest remark(String remark) {
        this.remark = remark;
        return this;
    }

    public List<SignFile> getSignFile() {
        return this.signFile;
    }

    public SmsSignRequest signFile(List<SignFile> signFile) {
        this.signFile = signFile;
        return this;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public SmsSignRequest pageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public SmsSignRequest pageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public static class SignFile {

        private String fileContent;

        private String fileSuffix;

        public String getFileContent() {
            return this.fileContent;
        }

        public SignFile fileContent(String fileContent) {
            this.fileContent = fileContent;
            return this;
        }

        public String getFileSuffix() {
            return this.fileSuffix;
        }

        public SignFile fileSuffix(String fileSuffix) {
            this.fileSuffix = fileSuffix;
            return this;
        }
    }
}
