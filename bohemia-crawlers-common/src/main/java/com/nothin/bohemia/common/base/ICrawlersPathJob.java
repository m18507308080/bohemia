package com.nothin.bohemia.common.base;

/*
 * 清洗数据的结果集接口
 */
public interface ICrawlersPathJob<T> {
	  //根据路径数组执行job
	  JobResult execute (T jobPathArray);
	  //根据异常路径数组执行job
	  JobResult executeError (T errorJobPathArray);
}