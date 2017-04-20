package com.nothin.bohemia.producer.website.xici.task;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import com.nothin.bohemia.common.base.ICrawlersTask;
import com.nothin.bohemia.common.base.ICrawlersTaskAssigner;
import com.nothin.bohemia.common.base.JobResult;
import com.nothin.bohemia.producer.website.xici.job.XiciJob;

public class XiciTask implements ICrawlersTask<BlockingQueue<String>> {

	private static Logger logger = Logger.getLogger(XiciTask.class);

	private BlockingQueue<String> params;
	private Map<String, Object> token;
	private ICrawlersTaskAssigner<BlockingQueue<String>> assigner;

	@Override
	public void run() {
		String currentThreadName = Thread.currentThread().getName();
		logger.info(" [xici.net.co] 线程[" + currentThreadName + "] 爬虫Task 开始工作 ");
		XiciJob job = new XiciJob();
		JobResult result = job.execute(params);
		assigner.finishTask(token, result);
	}

	@Override
	public void setup(BlockingQueue<String> params, Map<String, Object> token,
			ICrawlersTaskAssigner<BlockingQueue<String>> assigner) {
		this.params = params;
		this.token = token;
		this.assigner = assigner;
	}

}
