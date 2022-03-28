package com.jcohy.framework.starter.sms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;

import com.jcohy.framework.starter.sms.request.SmsSendRequest;
import com.jcohy.framework.utils.Sets;
import com.jcohy.framework.utils.StringUtils;
import com.jcohy.framework.utils.constant.StringPools;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/28/22:10:34
 * @since 2022.0.1
 */
public class SmsHelper {


	public static List<String> formatPhones(Collection<String> phones){
		return phones.stream().map(SmsHelper::formatPhone).collect(Collectors.toList());
	}

	public static String formatPhone(String phone){
		if(!StringUtils.startsWithAny(phone,"86","+86")) {
			phone = "86".concat(phone);
		}
		return phone;
	}

	public static <T extends Collection<?>> T validSize(final T collection, final int size, final String message, final Object... values) {
		Validate.notNull(collection);
		if (size < 0 || collection.size() > size ) {
			throw new IndexOutOfBoundsException(String.format(message, values));
		}
		return collection;
	}

	public static SmsSendRequest processSmsSendSign(SmsSendRequest request) {
		int phoneSize = request.getPhones().size();
		int signsSize = request.getSigns().size();

		// 如果签名数量大于 1 且和手机号不匹配
		if (signsSize > 1 && signsSize != phoneSize) {
			throw new SmsException("签名和手机号数量不匹配！");
		}
		// 如果签名和手机号不匹配，则默认都使用第一个签名
		if (phoneSize > 1 && signsSize != phoneSize) {
			String repeat = StringUtils.repeat(request.getSigns().iterator().next(), StringPools.COMMA, phoneSize);
			request.signs(StringUtils.defaultSplit(repeat));
		}
		return request;
	}

	public static List<Map<String, String>> processSmsSendTemplateParams(SmsSendRequest request) {

		Map<String, String> templateParams = request.getTemplateParams();
		Map<String, Supplier<String>> valueSupplier = request.getValueSupplier();

		/*
		 * 处理模版参数。数量需要和手机号，签名保持一致 首先获取两个 map 中相同的 key. 动态定义的 value 会覆盖静态定义的 value.
		 */
		List<Map<String, String>> templateParamsList = new ArrayList<>();
		Set<String> sameKeys = Sets.getSameKeys(valueSupplier.keySet(), templateParams.keySet());

		for (int i = 0; i < request.getPhones().size(); i++) {
			Map<String, String> templateParamsMap = new HashMap<>();
			for (Map.Entry<String, String> result : templateParams.entrySet()) {
				String key = result.getKey();
				String value = result.getValue();
				if (sameKeys.contains(key)) {
					value = valueSupplier.get(key).get();
				}
				templateParamsMap.put(result.getKey(), value);
			}
			templateParamsList.add(templateParamsMap);
		}
		return templateParamsList;
	}
}
