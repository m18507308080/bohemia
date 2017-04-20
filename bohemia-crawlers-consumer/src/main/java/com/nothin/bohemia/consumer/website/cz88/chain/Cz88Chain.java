package com.nothin.bohemia.consumer.website.cz88.chain;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import com.nothin.bohemia.common.base.ICrawlersTaskAssignerChain;
import com.nothin.bohemia.common.base.JobResult;
import com.nothin.bohemia.common.utils.TokenProcessor;
import com.nothin.bohemia.consumer.website.cz88.assigner.Cz88Assinger;
import com.nothin.onolisa.framework.redis.client.IRedisClient;

public class Cz88Chain implements ICrawlersTaskAssignerChain<IRedisClient> {
	
	private static Logger logger=Logger.getLogger(Cz88Chain.class);
	
	private IRedisClient params;
	private Map<String, Object> token;
	
	private Cz88Assinger cz88ConsumerAssinger;

	public Cz88Assinger getCz88ConsumerAssinger() {
		return cz88ConsumerAssinger;
	}

	public void setCz88ConsumerAssinger(Cz88Assinger cz88ConsumerAssinger) {
		this.cz88ConsumerAssinger = cz88ConsumerAssinger;
	}

	@Override
	public void setup(IRedisClient params) {
		this.params=params;
	}

	@Override
	public void doChain() {
		logger.info(" [cz88.net] 开始分发任务 ");
		token = new HashMap<String, Object>();
		token.put(TokenProcessor.getInstance().generateTokeCode(),new JobResult());
		token.put("doChainMethodNo", 1);
		cz88ConsumerAssinger.setup(params, token, this);
		cz88ConsumerAssinger.doAssign();
	}

	@Override
	public void finishTaskAssigner(Map<String, Object> token,JobResult jobResult) {
		
	}

}
