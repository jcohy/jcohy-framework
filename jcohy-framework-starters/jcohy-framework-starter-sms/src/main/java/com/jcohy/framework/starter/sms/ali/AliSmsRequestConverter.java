package com.jcohy.framework.starter.sms.ali;

import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.tea.TeaModel;
import com.jcohy.framework.starter.sms.SmsAction;
import com.jcohy.framework.starter.sms.SmsException;
import com.jcohy.framework.starter.sms.request.*;
import com.jcohy.framework.utils.JsonUtils;
import com.jcohy.framework.utils.ObjectUtils;
import com.jcohy.framework.utils.Sets;
import com.jcohy.framework.utils.StringUtils;
import com.jcohy.framework.utils.constant.StringPools;
import com.jcohy.framework.utils.maps.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.core.convert.converter.Converter;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2022/3/17:18:47
 * @since 2022.0.1
 */
public class AliSmsRequestConverter implements Converter<SmsRequest, TeaModel> {


    @Override
    public TeaModel convert(SmsRequest request) {
        TeaModel model = null;
        if(request instanceof SmsSendRequest) {
            model = buildSmsSendRequestTeaModel((SmsSendRequest)request);
        }
        if(request instanceof SmsQueryDetailsRequest) {
            model = buildSmsQueryDetailsRequestTeaModel((SmsQueryDetailsRequest)request);
        }
        if( request instanceof SmsSignRequest) {
            model = buildSmsSignRequestTeaModel((SmsSignRequest)request);
        }
        if( request instanceof SmsTemplateRequest) {
            model = buildSmsTemplateRequestTeaModel((SmsTemplateRequest)request);
        }
        if( request instanceof SmsShortUrlRequest) {
            model = buildSmsShortUrlRequestTeaModel((SmsShortUrlRequest)request);
        }
        if( request instanceof SmsTagRequest) {
            model = buildSmsTagRequestTeaModel((SmsTagRequest)request);
        }
        return model;
    }

    private TeaModel buildSmsSignRequestTeaModel(SmsSignRequest request) {

        List<SmsSignRequest.SignFile> signFile = request.getSignFile();

        if(request.getAction().equals(SmsAction.ADD_SMS_SIGN)) {
            List<AddSmsSignRequest.AddSmsSignRequestSignFileList> fileLists = new ArrayList<>(signFile.size());
            signFile.forEach((file) -> fileLists.add(new AddSmsSignRequest.AddSmsSignRequestSignFileList()
                    .setFileContents(file.getFileContent())
                    .setFileSuffix(file.getFileSuffix())));
            return new AddSmsSignRequest()
                    .setSignName(request.getSignName())
                    .setSignSource(request.getSignSource())
                    .setRemark(request.getRemark())
                    .setSignFileList(fileLists);
        }
        if(request.getAction().equals(SmsAction.DELETE_SMS_SIGN)) {
            return new DeleteSmsSignRequest().setSignName(request.getSignName());
        }
        if(request.getAction().equals(SmsAction.MODIFY_SMS_SIGN)) {
            List<ModifySmsSignRequest.ModifySmsSignRequestSignFileList> fileLists = new ArrayList<>(signFile.size());
            signFile.forEach((file) -> fileLists.add(new ModifySmsSignRequest.ModifySmsSignRequestSignFileList()
                    .setFileContents(file.getFileContent())
                    .setFileSuffix(file.getFileSuffix())));
            return new ModifySmsSignRequest()
                    .setSignName(request.getSignName())
                    .setSignSource(request.getSignSource())
                    .setRemark(request.getRemark())
                    .setSignFileList(fileLists);
        }
        if(request.getAction().equals(SmsAction.QUERY_SMS_SIGN)) {
            return new QuerySmsSignListRequest()
                    .setPageIndex(request.getPageIndex())
                    .setPageSize(request.getPageSize());
        }
        if(request.getAction().equals(SmsAction.QUERY_SMS_SIGN_STATUS)) {
            return new QuerySmsSignRequest()
                    .setSignName(request.getSignName());
        }
        return null;
    }

    private TeaModel buildSmsTemplateRequestTeaModel(SmsTemplateRequest request) {
        if(request.getAction().equals(SmsAction.ADD_SMS_TEMPLATE)) {
            return new AddSmsTemplateRequest()
                    .setTemplateType(request.getTemplateType())
                    .setTemplateName(request.getTemplateName())
                    .setTemplateContent(request.getTemplateContent())
                    .setRemark(request.getRemark());
        }
        if(request.getAction().equals(SmsAction.DELETE_SMS_TEMPLATE)) {
            return new DeleteSmsTemplateRequest()
                    .setTemplateCode(request.getTemplateCode());
        }
        if(request.getAction().equals(SmsAction.MODIFY_SMS_TEMPLATE)) {
            return new ModifySmsTemplateRequest()
                    .setTemplateType(request.getTemplateType())
                    .setTemplateName(request.getTemplateName())
                    .setTemplateCode(request.getTemplateCode())
                    .setTemplateContent(request.getTemplateContent())
                    .setRemark(request.getRemark());
        }
        if(request.getAction().equals(SmsAction.QUERY_SMS_TEMPLATE)) {
            return new QuerySmsTemplateListRequest()
                    .setPageIndex(request.getPageIndex())
                    .setPageSize(request.getPageSize());
        }
        if(request.getAction().equals(SmsAction.QUERY_SMS_TEMPLATE_STATUS)) {
            return new QuerySmsTemplateRequest().setTemplateCode(request.getTemplateCode());
        }
        return null;
    }

    private TeaModel buildSmsShortUrlRequestTeaModel(SmsShortUrlRequest request) {
        if(request.getAction().equals(SmsAction.ADD_SHORT_URL)) {
            return new AddShortUrlRequest()
                    .setSourceUrl(request.getSourceUrl())
                    .setShortUrlName(request.getShortUrlName())
                    .setEffectiveDays(request.getEffectiveDays());
        }
        if(request.getAction().equals(SmsAction.DELETE_SHORT_URL)) {
            return new DeleteShortUrlRequest().setSourceUrl(request.getSourceUrl());
        }
        if(request.getAction().equals(SmsAction.QUERY_SHORT_URL)) {
            return new QueryShortUrlRequest().setShortUrl(request.getShortUrl());
        }
        return null;
    }

    private TeaModel buildSmsTagRequestTeaModel(SmsTagRequest request) {
        if(request.getAction().equals(SmsAction.ADD_TAG)) {
            List<SmsTagRequest.SmsTag> tags = request.getTags();
            List<TagResourcesRequest.TagResourcesRequestTag> tagList = new ArrayList<>(tags.size());
            tags.forEach((tag) -> {
                tagList.add(new TagResourcesRequest.TagResourcesRequestTag()
                        .setKey(tag.getKey())
                        .setValue(tag.getValue()));
            });
            return new TagResourcesRequest()
                    .setResourceType(request.getResourceType())
                    .setRegionId(request.getRegionId())
                    .setProdCode(request.getProdCode())
                    .setTag(tagList)
                    .setResourceId(request.getResourcesId());
        }
        if(request.getAction().equals(SmsAction.DELETE_TAG)) {
            return new UntagResourcesRequest()
                    .setAll(request.isAll())
                    .setProdCode(request.getProdCode())
                    .setRegionId(request.getRegionId())
                    .setResourceType(request.getResourceType())
                    .setResourceId(request.getResourcesId())
                    .setTagKey(request.getTags().stream().map(SmsTagRequest.SmsTag::getKey).collect(Collectors.toList()));
        }
        if(request.getAction().equals(SmsAction.QUERY_TAG)) {
            List<SmsTagRequest.SmsTag> tags = request.getTags();
            List<ListTagResourcesRequest.ListTagResourcesRequestTag> tagList = new ArrayList<>(tags.size());
            tags.forEach((tag) -> {
                tagList.add(new ListTagResourcesRequest.ListTagResourcesRequestTag()
                        .setKey(tag.getKey())
                        .setValue(tag.getValue()));
            });
            return new ListTagResourcesRequest()
                    .setResourceType(request.getResourceType())
                    .setRegionId(request.getRegionId())
                    .setProdCode(request.getProdCode())
                    .setTag(tagList)
                    .setResourceId(request.getResourcesId())
                    .setNextToken(request.getNextToken())
                    .setPageSize(request.pageSize());
        }

        return null;
    }

    private TeaModel buildSmsQueryDetailsRequestTeaModel(SmsQueryDetailsRequest request) {

        if( request.getAction().equals(SmsAction.QUERY_SMS_DETAILS)) {
            return new QuerySendDetailsRequest()
                    .setBizId(request.getBizId())
                    .setPhoneNumber(request.getPhone())
                    .setSendDate(request.getSendDate())
                    .setPageSize(request.getPageSize())
                    .setCurrentPage(request.getPageIndex());
        }
        if( request.getAction().equals(SmsAction.QUERY_SMS_STATISTICS)) {
            return new QuerySendStatisticsRequest()
                    .setIsGlobe(request.getIsGlobe())
                    .setStartDate(request.getStartDate())
                    .setEndDate(request.getEndDate())
                    .setPageIndex(Math.toIntExact(request.getPageIndex()))
                    .setPageSize(Math.toIntExact(request.getPageSize()));
        }
        return null;
    }

    private TeaModel buildSmsSendRequestTeaModel(SmsSendRequest request) {
        Validate.notEmpty(request.getPhones());
        Validate.notEmpty(request.getSigns());
        Validate.notEmpty(request.getTemplateCode());
        Validate.notEmpty(request.getTemplateParams());
        int phoneSize = request.getPhones().size();
        int signsSize = request.getSigns().size();
        Map<String, String> templateParams = request.getTemplateParams();
        Map<String, Supplier<String>> valueSupplier = request.getValueSupplier();


        // 如果签名数量大于 1 且和手机号不匹配
        if(signsSize > 1 && signsSize != phoneSize) {
            throw new SmsException("签名和手机号不匹配");
        }
        // 如果签名和手机号不匹配，则默认都使用第一个签名
        if( phoneSize > 1 && signsSize != phoneSize) {
            String repeat = StringUtils.repeat(request.getSigns().iterator().next(), StringPools.COMMA, phoneSize);
            request.signs(StringUtils.defaultSplit(repeat));
        }

        /*
           处理模版参数。数量需要和手机号，签名保持一致
           首先获取两个 map 中相同的 key. 动态定义的 value 会覆盖静态定义的 value.
         */
        List<Map<String,String>> templateParamsList = new ArrayList<>();
        Set<String> sameKeys = Sets.getSameKeys(valueSupplier.keySet(), templateParams.keySet());


        for (int i = 0; i < phoneSize; i++) {
            Map<String,String> templateParamsMap = new HashMap<>();
            for (Map.Entry<String,String> result : templateParams.entrySet()) {
                String key = result.getKey();
                String value = result.getValue();
                if(sameKeys.contains(key)) {
                    value = valueSupplier.get(key).get();
                }
                templateParamsMap.put(result.getKey(),value);
            }
            templateParamsList.add(templateParamsMap);
        }

        if(request.getAction().equals(SmsAction.SEND_SMS)) {
            return new SendSmsRequest()
                    .setPhoneNumbers(JsonUtils.toJson(request.getPhones()))
                    .setSignName(JsonUtils.toJson(request.getSigns()))
                    .setTemplateCode(request.getTemplateCode())
                    .setTemplateParam(JsonUtils.toJson(request.getTemplateParams()));
        }
        if(request.getAction().equals(SmsAction.SEND_BATCH_SMS)) {
            return new SendBatchSmsRequest()
                    .setPhoneNumberJson(JsonUtils.toJson(request.getPhones()))
                    .setSignNameJson(JsonUtils.toJson(request.getSigns()))
                    .setTemplateCode(request.getTemplateCode())
                    .setTemplateParamJson(JsonUtils.toJson(templateParamsList));
        }
        return null;
    }
}
