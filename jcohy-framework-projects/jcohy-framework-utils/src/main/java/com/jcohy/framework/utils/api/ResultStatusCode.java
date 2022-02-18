package com.jcohy.framework.utils.api;

/**
 * 描述: 返回状态码.
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2/18/22:15:05
 * @since 2022.0.1
 */
public interface ResultStatusCode {

	/**
	 * 返回状态码.
	 * @return 返回状态码
	 */
	Integer getCode();

	/**
	 * 返回状态码.
	 * @return 返回消息
	 */
	String getMessage();

	/**
	 * 继续。客户端应继续其请求.
	 */
	int SC_CONTINUE = 100;

	/**
	 * 切换协议。服务器根据客户端的请求切换协议。只能切换到更高级的协议，例如，切换到HTTP的新版本协议.
	 */
	int SC_SWITCHING_PROTOCOLS = 101;

	/**
	 * 请求成功。一般用于GET与POST请求.
	 */
	int SC_OK = 200;

	/**
	 * 已创建。成功请求并创建了新的资源.
	 */
	int SC_CREATED = 201;

	/**
	 * 已接受。已经接受请求，但未处理完成.
	 */
	int SC_ACCEPTED = 202;

	/**
	 * 非授权信息。请求成功。但返回的meta信息不在原始的服务器，而是一个副本.
	 */
	int SC_NON_AUTHORITATIVE_INFORMATION = 203;

	/**
	 * 无内容。服务器成功处理，但未返回内容。在未更新网页的情况下，可确保浏览器继续显示当前文档.
	 */
	int SC_NO_CONTENT = 204;

	/**
	 * 重置内容。服务器处理成功，用户终端（例如：浏览器）应重置文档视图。可通过此返回码清除浏览器的表单域.
	 */
	int SC_RESET_CONTENT = 205;

	/**
	 * 部分内容。服务器成功处理了部分GET请求.
	 */
	int SC_PARTIAL_CONTENT = 206;

	/**
	 * 多种选择。请求的资源可包括多个位置，相应可返回一个资源特征与地址的列表用于用户终端（例如：浏览器）选择.
	 */
	int SC_MULTIPLE_CHOICES = 300;

	/**
	 * 永久移动。请求的资源已被永久的移动到新URI，返回信息会包括新的URI，浏览器会自动定向到新URI。今后任何新的请求都应使用新的URI代替.
	 */
	int SC_MOVED_PERMANENTLY = 301;

	/**
	 * 临时移动。与301类似。但资源只是临时被移动。客户端应继续使用原有URI.
	 */
	int SC_FOUND = 302;

	/**
	 * 查看其它地址。与301类似。使用GET和POST请求查看.
	 */
	int SC_SEE_OTHER = 303;

	/**
	 * 未修改。所请求的资源未修改，服务器返回此状态码时，不会返回任何资源。客户端通常会缓存访问过的资源，通过提供一个头信息指出客户端希望只返回在指定日期之后修改的资源.
	 */
	int SC_NOT_MODIFIED = 304;

	/**
	 * 使用代理。所请求的资源必须通过 <code><em>Location</em></code> 字段提供的代理访问.
	 */
	int SC_USE_PROXY = 305;

	/**
	 * 临时重定向。与302类似。使用 GET 请求重定向。URI 可以通过响应中的 <code><em>Location</em></code> 字段.
	 */
	int SC_TEMPORARY_REDIRECT = 307;

	/**
	 * 客户端请求的语法错误，服务器无法理解.
	 */
	int SC_BAD_REQUEST = 400;

	/**
	 * 请求要求用户的身份认证.
	 */
	int SC_UNAUTHORIZED = 401;

	/**
	 * 保留，将来使用.
	 */
	int SC_PAYMENT_REQUIRED = 402;

	/**
	 * 服务器理解请求客户端的请求，但是拒绝执行此请求.
	 */
	int SC_FORBIDDEN = 403;

	/**
	 * 服务器无法根据客户端的请求找到资源（网页）。通过此代码，网站设计人员可设置"您所请求的资源无法找到"的个性页面.
	 */
	int SC_NOT_FOUND = 404;

	/**
	 * 客户端 <code><em>Request-URI</em></code> 标识的资源不允许使用
	 * <code><em>Request-Line</em></code>. 中指定的方法
	 */
	int SC_METHOD_NOT_ALLOWED = 405;

	/**
	 * 服务器无法根据客户端请求的内容特性完成请求.
	 */
	int SC_NOT_ACCEPTABLE = 406;

	/**
	 * 请求要求代理的身份认证，与401类似，但请求者应当使用代理进行授权.
	 */
	int SC_PROXY_AUTHENTICATION_REQUIRED = 407;

	/**
	 * 服务器等待客户端发送的请求时间过长，超时.
	 */
	int SC_REQUEST_TIMEOUT = 408;

	/**
	 * 服务器完成客户端的 PUT 请求时可能返回此代码，服务器处理请求时发生了冲突.
	 */
	int SC_CONFLICT = 409;

	/**
	 * 客户端请求的资源已经不存在。410不同于404，如果资源以前有现在被永久删除了可使用410代码，网站设计人员可通过301代码指定资源的新位置.
	 */
	int SC_GONE = 410;

	/**
	 * 服务器无法处理客户端发送的不带 <code><em>Content-Length</em></code> 的请求信息.
	 */
	int SC_LENGTH_REQUIRED = 411;

	/**
	 * 客户端请求信息的先决条件错误.
	 */
	int SC_PRECONDITION_FAILED = 412;

	/**
	 * 由于请求的实体过大，服务器无法处理，因此拒绝请求。为防止客户端的连续请求，服务器可能会关闭连接。如果只是服务器暂时无法处理，则会包含一个
	 * <code><em>Retry-After</em></code> 的响应信息.
	 */
	int SC_REQUEST_ENTITY_TOO_LARGE = 413;

	/**
	 * 请求的 <code><em>Request-URI</em></code> 过长（URI通常为网址），服务器无法处理.
	 */
	int SC_REQUEST_URI_TOO_LONG = 414;

	/**
	 * 服务器无法处理请求附带的媒体格式.
	 */
	int SC_UNSUPPORTED_MEDIA_TYPE = 415;

	/**
	 * 客户端请求的范围无效.
	 */
	int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;

	/**
	 * 服务器无法满足Expect的请求头信息.
	 */
	int SC_EXPECTATION_FAILED = 417;

	/**
	 * 服务器内部错误，无法完成请求.
	 */
	int SC_INTERNAL_SERVER_ERROR = 500;

	/**
	 * 服务器不支持请求的功能，无法完成请求.
	 */
	int SC_NOT_IMPLEMENTED = 501;

	/**
	 * 作为网关或者代理工作的服务器尝试执行请求时，从远程服务器接收到了一个无效的响应.
	 */
	int SC_BAD_GATEWAY = 502;

	/**
	 * 由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的 <code><em>Retry-After</em></code> 头信息中.
	 */
	int SC_SERVICE_UNAVAILABLE = 503;

	/**
	 * 充当网关或代理的服务器，未及时从远端服务器获取请求.
	 */
	int SC_GATEWAY_TIMEOUT = 504;

	/**
	 * 服务器不支持请求的HTTP协议的版本，无法完成处理.
	 */
	int SC_HTTP_VERSION_NOT_SUPPORTED = 505;
}
