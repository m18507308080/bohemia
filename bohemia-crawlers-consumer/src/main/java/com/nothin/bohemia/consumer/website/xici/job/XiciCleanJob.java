package com.nothin.bohemia.consumer.website.xici.job;

import java.io.File;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.Element;

import com.nothin.bohemia.common.base.CrawlersTaskPool;
import com.nothin.bohemia.common.base.ICrawlersPathJob;
import com.nothin.bohemia.common.base.JobResult;
import com.nothin.bohemia.common.utils.Constant;
import com.nothin.bohemia.common.utils.NetworkAccess;
import com.nothin.bohemia.common.utils.XmlUtils;
import com.nothin.onolisa.framework.redis.client.IRedisClient;

public class XiciCleanJob implements ICrawlersPathJob<IRedisClient> {

	private static Logger logger = Logger.getLogger(XiciCleanJob.class);
	
	@Override
	public JobResult execute(IRedisClient jobPathArray) {
		String currentThreadName = Thread.currentThread().getName();
		logger.info(" [xici.net.co] 线程[" + currentThreadName + "] 爬虫JOB 开始工作 ");
		JobResult jobResult = new JobResult();
		String url = null  ;
		try {
			while ((url = jobPathArray.lpop(Constant.WEBSITE_CODE_QUEUE_XICI)) != null) {
				System.out.println(" 读取远程队列数据 : " + url);
				String html = null ;
				try {
					html = NetworkAccess.getInstance().getHttpDocument(url);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Document document =Jsoup.parse(html) ;
				System.err.println(document.text());
			}
		} catch (Exception e) {
			logger.info("Redis 连接异常 , 网络是否畅通 , 服务是否启动") ;
		}
		if (null == url) {
			logger.info(" [xici.net.co] 线程[" + currentThreadName + "] 发现队列为空 停止工作 ");
			CrawlersTaskPool.sharedInstance().getExecutor().shutdown();
		}
		logger.info(" [xici.net.co] 线程[" + currentThreadName + "] 爬虫JOB 结束工作 ");
		return jobResult ;
	}

	@Override
	public JobResult executeError(IRedisClient errorJobPathArray) {
		return null;
	}
	
	public void xml (String host , String port) {
	    Element proxys = XmlUtils.createRootElement("proxys");  
	    Element proxy = XmlUtils.createRootElement("proxy") ;
	    proxy.setAttribute("host", host) ;
	    proxy.setAttribute("port", port) ;
	    XmlUtils.appendElement(proxys, proxy);
	    XmlUtils.saveToXml(proxys, new File("C:/1.xml"));
	}

}
