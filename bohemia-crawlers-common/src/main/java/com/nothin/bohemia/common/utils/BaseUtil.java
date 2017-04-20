package com.nothin.bohemia.common.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nothin.onolisa.framework.redis.client.IRedisClient;

public class BaseUtil {
	
	private BaseUtil () {}
	
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	private static IRedisClient springRedisClient ;
	private static BaseUtil baseUtil = new BaseUtil() ;
	
	public static BaseUtil getInstance() {
		return baseUtil ;
	}
	
	public ApplicationContext getApplicationContext () {
		return applicationContext ;
	}
	
	public IRedisClient getRedisClient () {
		return springRedisClient ;
	}
	
	static {
		springRedisClient = (IRedisClient) applicationContext.getBean("springRedisClient") ;
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * 
	 * @param obj
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(Object obj) {
		return obj == null || "".equals(obj) || "null".equals(obj);
	}
	
	
}
