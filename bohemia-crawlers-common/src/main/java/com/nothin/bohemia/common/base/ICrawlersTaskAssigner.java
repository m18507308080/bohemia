package com.nothin.bohemia.common.base;

import java.util.Map;

/*
 * 分发类接口,创建多线程Task任务类
 */
public interface ICrawlersTaskAssigner<T> {
	void setup(T params, Map<String, Object> token, ICrawlersTaskAssignerChain<T> chain);
	void doAssign();
	void finishTask(Map<String, Object> token, JobResult jobResult);
}