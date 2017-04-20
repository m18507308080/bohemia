package com.nothin.bohemia.producer.website.xici.chain;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import com.nothin.bohemia.common.base.ICrawlersTaskAssignerChain;
import com.nothin.bohemia.common.base.JobResult;
import com.nothin.bohemia.common.utils.TokenProcessor;
import com.nothin.bohemia.producer.website.xici.assigner.XiciAssinger;

public class XiciChain implements ICrawlersTaskAssignerChain<BlockingQueue<String>> {
	
	private static Logger logger=Logger.getLogger(XiciChain.class);
	
	private BlockingQueue<String> params;
	private Map<String, Object> token;
	
	private XiciAssinger xiciProducerAssinger;

	public XiciAssinger getXiciProducerAssinger() {
		return xiciProducerAssinger;
	}

	public void setXiciProducerAssinger(XiciAssinger xiciProducerAssinger) {
		this.xiciProducerAssinger = xiciProducerAssinger;
	}

	@Override
	public void setup(BlockingQueue<String> params) {
		this.params=params;
	}

	@Override
	public void doChain() {
		logger.info(" [xici.net.co] 开始分发任务 ");
		token = new HashMap<String, Object>();
		token.put(TokenProcessor.getInstance().generateTokeCode(),new JobResult());
		token.put("doChainMethodNo", 1);
		xiciProducerAssinger.setup(params, token, this);
		xiciProducerAssinger.doAssign();
	}

	@Override
	public void finishTaskAssigner(Map<String, Object> token,JobResult jobResult) {
		
	}

}
