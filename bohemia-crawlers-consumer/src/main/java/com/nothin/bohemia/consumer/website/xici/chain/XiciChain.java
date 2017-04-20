package com.nothin.bohemia.consumer.website.xici.chain;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import com.nothin.bohemia.common.base.ICrawlersTaskAssignerChain;
import com.nothin.bohemia.common.base.JobResult;
import com.nothin.bohemia.common.utils.TokenProcessor;
import com.nothin.bohemia.consumer.website.xici.assigner.XiciAssinger;
import com.nothin.onolisa.framework.redis.client.IRedisClient;

public class XiciChain implements ICrawlersTaskAssignerChain<IRedisClient> {
	
	private static Logger logger=Logger.getLogger(XiciChain.class);
	
	private IRedisClient params;
	private Map<String, Object> token;
	
	private XiciAssinger xiciConsumerAssinger;

	public XiciAssinger getXiciConsumerAssinger() {
		return xiciConsumerAssinger;
	}

	public void setXiciConsumerAssinger(XiciAssinger xiciConsumerAssinger) {
		this.xiciConsumerAssinger = xiciConsumerAssinger;
	}

	@Override
	public void setup(IRedisClient params) {
		this.params=params;
	}

	@Override
	public void doChain() {
		logger.info(" [xici.net.co] 开始分发任务 ");
		token = new HashMap<String, Object>();
		token.put(TokenProcessor.getInstance().generateTokeCode(),new JobResult());
		token.put("doChainMethodNo", 1);
		xiciConsumerAssinger.setup(params, token, this);
		xiciConsumerAssinger.doAssign();
	}

	@Override
	public void finishTaskAssigner(Map<String, Object> token,JobResult jobResult) {
		
	}

}
