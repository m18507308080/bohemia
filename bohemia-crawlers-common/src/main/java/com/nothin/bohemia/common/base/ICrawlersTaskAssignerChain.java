package com.nothin.bohemia.common.base;

import java.util.Map;

/*
 * 总控制分发类,将Assigner形成一个执行链
 */
public interface ICrawlersTaskAssignerChain<T> {
	void setup(T params);
	void doChain();
	void finishTaskAssigner(Map<String, Object> token,JobResult jobResult);
}