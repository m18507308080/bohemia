package com.nothin.bohemia.consumer.bootstrap;

import com.nothin.bohemia.common.utils.BaseUtil;
import com.nothin.bohemia.consumer.website.cz88.chain.Cz88Chain;

public class BootStrart {

	public static void main(String[] args) {
//		XiciChain xiciChain = (XiciChain) BaseUtil.getInstance().getApplicationContext().getBean("xiciConsumerChain") ;
//		xiciChain.setup(BaseUtil.getInstance().getRedisClient());
//		xiciChain.doChain();
		
		Cz88Chain cz88Chain = (Cz88Chain) BaseUtil.getInstance().getApplicationContext().getBean("cz88ConsumerChain") ;
		cz88Chain.setup(BaseUtil.getInstance().getRedisClient());
		cz88Chain.doChain();
	}
	
}
