package com.nothin.bohemia.consumer.website.cz88.task;

import java.util.Map;
import org.apache.log4j.Logger;
import com.nothin.bohemia.common.base.ICrawlersTask;
import com.nothin.bohemia.common.base.ICrawlersTaskAssigner;
import com.nothin.bohemia.common.base.JobResult;
import com.nothin.bohemia.consumer.website.cz88.job.Cz88CleanJob;
import com.nothin.onolisa.framework.redis.client.IRedisClient;

public class Cz88Task implements ICrawlersTask<IRedisClient> {

	private static Logger logger = Logger.getLogger(Cz88Task.class);

	private IRedisClient params;
	private Map<String, Object> token;
	private ICrawlersTaskAssigner<IRedisClient> assigner;

	@Override
	public void run() {
		String currentThreadName = Thread.currentThread().getName();
		logger.info(" [cz88.net] 线程[" + currentThreadName + "] 爬虫Task 开始工作 ");
		Cz88CleanJob job = new Cz88CleanJob();
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
