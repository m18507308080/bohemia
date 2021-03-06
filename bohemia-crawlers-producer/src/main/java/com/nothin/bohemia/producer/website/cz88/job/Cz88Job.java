package com.nothin.bohemia.producer.website.cz88.job;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import com.nothin.bohemia.common.base.CrawlersTaskPool;
import com.nothin.bohemia.common.base.ICrawlersUrlJob;
import com.nothin.bohemia.common.base.JobResult;
import com.nothin.bohemia.common.utils.BaseUtil;
import com.nothin.bohemia.common.utils.Constant;

public class Cz88Job implements ICrawlersUrlJob<BlockingQueue<String>> {

	private static Logger logger = Logger.getLogger(Cz88Job.class);
	
	@Override
	public JobResult execute(BlockingQueue<String> jobPathArray) {
		String currentThreadName = Thread.currentThread().getName();
		logger.info(" [cz88.net] 线程[" + currentThreadName + "] 爬虫JOB 开始工作 ");
		JobResult jobResult = new JobResult();
		String url = null  ;
		try {
			while ((url = jobPathArray.poll()) != null) {
				BaseUtil.getInstance().getRedisClient().lpush(Constant.WEBSITE_CODE_QUEUE_CZ88, url) ;
			}
		} catch (Exception e) {
			logger.info("Redis 连接异常 , 网络是否畅通 , 服务是否启动") ;
		}
		if (null == url) {
			logger.info(" [cz88.net] 线程[" + currentThreadName + "] 发现队列为空 停止工作 ");
			CrawlersTaskPool.sharedInstance().getExecutor().shutdown();
		}
		logger.info(" [cz88.net] 线程[" + currentThreadName + "] 爬虫JOB 结束工作 ");
		return jobResult ;
	}

	@Override
	public JobResult executeError(BlockingQueue<String> errorJobPathArray) {
		return null;
	}

}
