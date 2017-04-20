package com.nothin.bohemia.producer.bootstrap;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.nothin.bohemia.common.utils.BaseUtil;
import com.nothin.bohemia.producer.website.cz88.chain.Cz88Chain;

public class BootStrart {

	public static BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	
	public static void main(String[] args) {
//		initXiciQueue();
//		XiciChain xiciChain = (XiciChain) BaseUtil.getInstance().getApplicationContext().getBean("xiciProducerChain") ;
//		xiciChain.setup(queue);
//		xiciChain.doChain();
		initCz88Queue() ;
		Cz88Chain cz88Chain = (Cz88Chain) BaseUtil.getInstance().getApplicationContext().getBean("cz88ProducerChain") ;
		cz88Chain.setup(queue);
		cz88Chain.doChain();
	}
	
	/***
	 * 初始化本地队列
	 */
	public static void initCz88Queue() {
		queue.offer("http://www.cz88.net/proxy/index.aspx");
		queue.offer("http://www.cz88.net/proxy/http_2.aspx");
		queue.offer("http://www.cz88.net/proxy/http_3.aspx");
		queue.offer("http://www.cz88.net/proxy/http_4.aspx");
		queue.offer("http://www.cz88.net/proxy/http_5.aspx");
		queue.offer("http://www.cz88.net/proxy/http_6.aspx");
		queue.offer("http://www.cz88.net/proxy/http_7.aspx");
		queue.offer("http://www.cz88.net/proxy/http_8.aspx");
		queue.offer("http://www.cz88.net/proxy/http_9.aspx");
		queue.offer("http://www.cz88.net/proxy/http_10.aspx");
		queue.offer("http://www.cz88.net/proxy/socks4.aspx");
		queue.offer("http://www.cz88.net/proxy/socks4_2.aspx");
		queue.offer("http://www.cz88.net/proxy/socks4_3.aspx");
		queue.offer("http://www.cz88.net/proxy/socks5.aspx");
		queue.offer("http://www.cz88.net/proxy/socks5_2.aspx");
	}
	
	/***
	 * 初始化本地队列
	 */
	public static void initXiciQueue() {
		for (int index = 1; index <= 178; index++) {
			queue.offer("http://www.xici.net.co/nn/" + index);
		}
		
		for (int index = 1; index <= 42; index++) {
			queue.offer("http://www.xici.net.co/nt/" + index);
		}
		
		for (int index = 1; index <= 122; index++) {
			queue.offer("http://www.xici.net.co/wn/" + index);
		}
		
		for (int index = 1; index <= 177; index++) {
			queue.offer("http://www.xici.net.co/wt/" + index);
		}
	}
}
