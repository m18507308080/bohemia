package com.nothin.bohemia.consumer.website.xici.task;

import java.util.Map;
import org.apache.log4j.Logger;
import com.nothin.bohemia.common.base.ICrawlersTask;
import com.nothin.bohemia.common.base.ICrawlersTaskAssigner;
import com.nothin.bohemia.common.base.JobResult;
import com.nothin.bohemia.consumer.website.xici.job.XiciCleanJob;
import com.nothin.onolisa.framework.redis.client.IRedisClient;

public class XiciTask implements ICrawlersTask<IRedisClient> {

	private static Logger logger = Logger.getLogger(XiciTask.class);

	private IRedisClient params;
	private Map<String, Object> token;
	private ICrawlersTaskAssigner<IRedisClient> assigner;

	@Override
	public void run() {
		String currentThreadName = Thread.currentThread().getName();
		logger.info(" [xici.net.co] 线程[" + currentThreadName + "] 爬虫Task 开始工作 ");
		XiciCleanJob job = new XiciCleanJob();
		JobResult result = job.execute(params);
		assigner.finishTask(token, result);
	}

	@Override
	public void setup(IRedisClient params, Map<String, Object> token,
			ICrawlersTaskAssigner<IRedisClient> assigner) {
		this.params = params;
		this.token = token;
		this.assigner = assigner;
	}

}
