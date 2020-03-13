package com.github.ore.framework.web.api;

/**
 * 约定： 返回码和文字描述格式：n_文字描述，使用的时候是通过按照位数截取。 ResultCode.java 这个类就不需要要了
 * 定义为接口，方便其他工程继承，添加自己的返回码和返回信息
 *
 */
public abstract class ResultMessage {

	/**
	 * 预定义返回码
	 */
	public static final String SYSTEM_I1 = "-1^系统繁忙，此时请开发者稍候再试";
	public static final String SYSTEM_0 = "0^请求成功";
	public static final String SYSTEM_1 = "1^请求失败";
	public static final String SYSTEM_2 = "2^系统繁忙，请稍后再试";
	public static final String SYSTEM_3 = "3^网络连接失败";
	public static final String SYSTEM_4 = "4^请求参数类型错误异常的捕获";
	public static final String SYSTEM_5 = "5^系统异常";

	public static final String HTTP_OK_200 = "200^请求成功。一般用于GET与POST请求";
	public static final String HTTP_ERROR_201 = "201^已创建。成功请求并创建了新的资源";
	public static final String HTTP_ERROR_202 = "202^已接受。已经接受请求，但未处理完成";
	public static final String HTTP_ERROR_203 = "203^非授权信息。请求成功。但返回的meta信息不在原始的服务器，而是一个副本";
	public static final String HTTP_ERROR_204 = "204^无内容。服务器成功处理，但未返回内容。在未更新网页的情况下，可确保浏览器继续显示当前文档";
	public static final String HTTP_ERROR_205 = "205^重置内容。服务器处理成功，用户终端（例如：浏览器）应重置文档视图。可通过此返回码清除浏览器的表单域";
	public static final String HTTP_ERROR_206 = "206^部分内容。服务器成功处理了部分GET请求";

	public static final String HTTP_ERROR_300 = "300^多种选择。请求的资源可包括多个位置，相应可返回一个资源特征与地址的列表用于用户终端（例如：浏览器）选择";
	public static final String HTTP_ERROR_301 = "301^永久移动。请求的资源已被永久的移动到新URI，返回信息会包括新的URI，浏览器会自动定向到新URI。今后任何新的请求都应使用新的URI代替";
	public static final String HTTP_ERROR_302 = "302^临时移动。与301类似。但资源只是临时被移动。客户端应继续使用原有URI";
	public static final String HTTP_ERROR_303 = "303^查看其它地址。与301类似。使用GET和POST请求查看";
	public static final String HTTP_ERROR_304 = "304^未修改。所请求的资源未修改，服务器返回此状态码时，不会返回任何资源。客户端通常会缓存访问过的资源，通过提供一个头信息指出客户端希望只返回在指定日期之后修改的资源";
	public static final String HTTP_ERROR_305 = "305^使用代理。所请求的资源必须通过代理访问";
	public static final String HTTP_ERROR_306 = "306^已经被废弃的HTTP状态码";
	public static final String HTTP_ERROR_307 = "307^临时重定向。与302类似。使用GET请求重定向";

	public static final String HTTP_ERROR_400 = "400^客户端请求的语法错误，服务器无法理解 ";
	public static final String HTTP_ERROR_401 = "401^请求要求用户的身份认证 ";
	public static final String HTTP_ERROR_402 = "402^保留，将来使用 ";
	public static final String HTTP_ERROR_403 = "403^服务器理解请求客户端的请求，但是拒绝执行此请求";
	public static final String HTTP_ERROR_404 = "404^服务器无法根据客户端的请求找到资源（网页）。通过此代码，网站设计人员可设置您所请求的资源无法找到的个性页面 ";
	public static final String HTTP_ERROR_405 = "405^客户端请求中的方法被禁止 ";
	public static final String HTTP_ERROR_406 = "406^服务器无法根据客户端请求的内容特性完成请求 ";
	public static final String HTTP_ERROR_407 = "407^请求要求代理的身份认证，与401类似，但请求者应当使用代理进行授权 ";
	public static final String HTTP_ERROR_408 = "408^服务器等待客户端发送的请求时间过长，超时 ";
	public static final String HTTP_ERROR_409 = "409^服务器完成客户端的 PUT 请求时可能返回此代码，服务器处理请求时发生了冲突 ";
	public static final String HTTP_ERROR_410 = "410^客户端请求的资源已经不存在。410不同于404，如果资源以前有现在被永久删除了可使用410代码，网站设计人员可通过301代码指定资源的新位置";
	public static final String HTTP_ERROR_411 = "411^服务器无法处理客户端发送的不带Content-Length的请求信息";
	public static final String HTTP_ERROR_412 = "412^客户端请求信息的先决条件错误";
	public static final String HTTP_ERROR_413 = "413^由于请求的实体过大，服务器无法处理，因此拒绝请求。为防止客户端的连续请求，服务器可能会关闭连接。如果只是服务器暂时无法处理，则会包含一个Retry-After的响应信息";
	public static final String HTTP_ERROR_414 = "414^请求的URI过长（URI通常为网址），服务器无法处理";
	public static final String HTTP_ERROR_415 = "415^服务器无法处理请求附带的媒体格式";
	public static final String HTTP_ERROR_416 = "416^客户端请求的范围无效";
	public static final String HTTP_ERROR_417 = "417^服务器无法满足Expect的请求头信息 ";

	public static final String HTTP_ERROR_500 = "500^服务器内部错误，无法完成请求";
	public static final String HTTP_ERROR_501 = "501^服务器不支持请求的功能，无法完成请求";
	public static final String HTTP_ERROR_502 = "502^作为网关或者代理工作的服务器尝试执行请求时，从远程服务器接收到了一个无效的响应";
	public static final String HTTP_ERROR_503 = "503^服务不可用，由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中";
	public static final String HTTP_ERROR_504 = "504^网关超时，充当网关或代理的服务器，未及时从远端服务器获取请求";
	public static final String HTTP_ERROR_505 = "505^服务器不支持请求的HTTP协议的版本，无法完成处理";
	public static final String HTTP_ERROR_506 = "506^变量也是导航";
	public static final String HTTP_ERROR_507 = "507^存储不足";
	public static final String HTTP_ERROR_508 = "508^发现环路";
	public static final String HTTP_ERROR_509 = "509^频带宽度超出限制(Apache的扩展)";
	public static final String HTTP_ERROR_511 = "511^需要网络授权";
	public static final String HTTP_ERROR_520 = "520^未知错误";

	/**
	 * 自定义应用异常码：APP_1XXXX
	 */
	public static final String APP_10000 = "10000^应用正常";

	/**
	 * 自定义业务返回码：BIZ_2XXXX,BIZ_3XXXX,BIZ_4XXXX,BIZ_5XXXX 等
	 */
	public static final String BIZ_20000 = "20000^业务正常";
	public static final String BIZ_20001 = "20001^请求参数不能为空";

}
