package com.nothin.bohemia.common.utils;



public class Constant {
	/**
	 * 模拟普通浏览器访问标记
	 */
	public final static String USERAGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)";

	/**
	 * 爬虫代理IP库
	 */
	public final static String DEFAULT_PROXY = String.valueOf(Constant.class.getProtectionDomain().getCodeSource().getLocation()).replace("file:/", "") + "defaultProxy.xml" ;

	/**
	 * xici.net.co Queue
	 */
	public final static String WEBSITE_CODE_QUEUE_XICI = "XICINETCO_URI_QUEUE" ;
	
	/**
	 * cz88.net Queue
	 */
	public final static String WEBSITE_CODE_QUEUE_CZ88 = "CZ88NET_URI_QUEUE" ;
}
