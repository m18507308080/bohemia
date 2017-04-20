package com.nothin.bohemia.consumer.website.cz88.job;

import java.io.File;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;

import com.nothin.bohemia.common.base.CrawlersTaskPool;
import com.nothin.bohemia.common.base.ICrawlersPathJob;
import com.nothin.bohemia.common.base.JobResult;
import com.nothin.bohemia.common.utils.Constant;
import com.nothin.bohemia.common.utils.NetworkAccess;
import com.nothin.bohemia.common.utils.XmlUtils;
import com.nothin.onolisa.framework.redis.client.IRedisClient;

public class Cz88CleanJob implements ICrawlersPathJob<IRedisClient> {

	private static Logger logger = Logger.getLogger(Cz88CleanJob.class);
	
	@Override
	public JobResult execute(IRedisClient jobPathArray) {
		Element proxys = XmlUtils.createRootElement("proxys");  
		
		String currentThreadName = Thread.currentThread().getName();
		logger.info(" [cz88.net] 线程[" + currentThreadName + "] 爬虫JOB 开始工作 ");
		JobResult jobResult = new JobResult();
		String url = null  ;
		try {
			while ((url = jobPathArray.lpop(Constant.WEBSITE_CODE_QUEUE_CZ88)) != null) {
				System.out.println(" 读取远程队列数据 : " + url);
				String html = null ;
				try {
					html = NetworkAccess.getInstance().getHttpDocumentByRandomProxy(url);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Document document = Jsoup.parse(html) ;
				Elements links = document.select("tbody > tr");
				for (org.jsoup.nodes.Element link : links) {
					if (link.child(0).text().indexOf("IP") >= 0 || link.child(0).text().indexOf("创") >= 0) {
						continue ;
					}
					if (link.child(0).text().equals("")) {
						continue ;
					}
					Element proxy = XmlUtils.createRootElement("proxy") ;
					proxy.setAttribute("host", link.child(0).text());
					proxy.setAttribute("port", link.child(1).text());
					XmlUtils.appendElement(proxys, proxy);
				}
				
			}
		} catch (Exception e) {
			logger.info("Redis 连接异常 , 网络是否畅通 , 服务是否启动") ;
		}
		if (null == url) {
			logger.info(" [cz88.net] 线程[" + currentThreadName + "] 发现队列为空 停止工作 ");
			CrawlersTaskPool.sharedInstance().getExecutor().shutdown();
		}
		logger.info(" [cz88.net] 线程[" + currentThreadName + "] 爬虫JOB 结束工作 ");
		XmlUtils.saveToXml(proxys, new File("C:/1.xml")) ;
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
