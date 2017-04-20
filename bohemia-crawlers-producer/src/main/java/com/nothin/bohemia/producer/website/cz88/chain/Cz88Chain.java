package com.nothin.bohemia.producer.website.cz88.chain;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import org.apache.log4j.Logger;
import com.nothin.bohemia.common.base.ICrawlersTaskAssignerChain;
import com.nothin.bohemia.common.base.JobResult;
import com.nothin.bohemia.common.utils.TokenProcessor;
import com.nothin.bohemia.producer.website.cz88.assigner.Cz88Assinger;

public class Cz88Chain implements ICrawlersTaskAssignerChain<BlockingQueue<String>> {
	
	private static Logger logger=Logger.getLogger(Cz88Chain.class);
	
	private BlockingQueue<String> params;
	private Map<String, Object> token;
	
	private Cz88Assinger cz88ProducerAssinger;

	public Cz88Assinger getCz88ProducerAssinger() {
		return cz88ProducerAssinger;
	}

	public void setCz88ProducerAssinger(Cz88Assinger cz88ProducerAssinger) {
		this.cz88ProducerAssinger = cz88ProducerAssinger;
	}

	@Override
	public void setup(BlockingQueue<String> params) {
		this.params=params;
	}

	@Override
	public void doChain() {
		logger.info(" [cz88.net] 开始分发任务 ");
		token = new HashMap<String, Object>();
		token.put(TokenProcessor.getInstance().generateTokeCode(),new JobResult());
		token.put("doChainMethodNo", 1);
		cz88ProducerAssinger.setup(params, token, this);
		cz88ProducerAssinger.doAssign();
	}

	@Override
	public void finishTaskAssigner(Map<String, Object> token,JobResult jobResult) {
		
	}

}
