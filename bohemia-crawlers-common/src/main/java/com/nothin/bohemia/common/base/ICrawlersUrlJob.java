package com.nothin.bohemia.common.base;

/*
 * 抓数据的结果集接口
 */
public interface ICrawlersUrlJob<T> {
	  //根据URL数组执行job
	  JobResult execute (T url);
	  //根据异常路径数组执行job
	  JobResult executeError (T errorURLArray);

}