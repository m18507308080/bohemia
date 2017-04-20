package com.nothin.bohemia.consumer.website.cz88.assigner;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nothin.bohemia.common.base.CrawlersTaskPool;
import com.nothin.bohemia.common.base.ICrawlersTaskAssigner;
import com.nothin.bohemia.common.base.ICrawlersTaskAssignerChain;
import com.nothin.bohemia.common.base.JobResult;
import com.nothin.bohemia.common.utils.BaseUtil;
import com.nothin.bohemia.common.utils.TokenProcessor;
import com.nothin.bohemia.consumer.website.cz88.task.Cz88Task;
import com.nothin.bohemia.consumer.website.xici.task.XiciTask;
import com.nothin.onolisa.framework.redis.client.IRedisClient;

public class Cz88Assinger implements ICrawlersTaskAssigner<IRedisClient> {
	
	private static Logger logger=Logger.getLogger(Cz88Assinger.class);
	
	private IRedisClient params;
	private Map<String, Object> token;
	private ICrawlersTaskAssignerChain<IRedisClient> chain;

	@Override
	public void setup(IRedisClient params, Map<String, Object> token,ICrawlersTaskAssignerChain<IRedisClient> chain) {
		this.params=params;
		this.token=token;
		this.chain=chain;
	}

	@Override
	public void doAssign() {
		CrawlersTaskPool pool = CrawlersTaskPool.sharedInstance();
		Map<String, Object> token = new HashMap<String, Object>();
		token.put("taskAllFinished", true);
		int threadCount = 10;
		logger.info(" [cz88.net] 构建 [" + threadCount + "] 个线程 爬虫 开始工作 ");
		for (int i = 1; i <= threadCount; i++) {
			Cz88Task task=new Cz88Task();
			token.put(TokenProcessor.getInstance().generateTokeCode(), null);
			task.setup(params, token, this);
			pool.doTask(task);
		}
	}

	@Override
	public void finishTask(Map<String, Object> token, JobResult jobResult) {
		for (Map.Entry<String, Object> entry : token.entrySet()) {
			if (BaseUtil.isEmpty(entry.getValue())) {
				return;
			}
		}
		JobResult jr = new JobResult();
		jr.setJobStatus((Boolean) token.get("taskAllFinished") ? JobResult.JOBOK_PATH  : JobResult.JOBPARTOK_PATH);
		chain.finishTaskAssigner(this.token, jr);
	}

}
