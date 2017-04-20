package com.nothin.bohemia.common.base;

import java.util.Map;

/*
 * 任务接口
 */
public interface ICrawlersTask<T> extends Runnable{
	 void setup(T params,Map<String, Object> token,ICrawlersTaskAssigner<T> assigner);
}