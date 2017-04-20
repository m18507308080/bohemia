package com.nothin.bohemia.common.base;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * 操作任务Task时，用此线程池
 */
public class CrawlersTaskPool {

	/**
	 * 生成单例类
	 * 
	 * @return
	 */
	private static CrawlersTaskPool instance = new CrawlersTaskPool();

	private static final int corePoolSize = 20;
	private static final int maximumPoolSize = 100;
	private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(maximumPoolSize);
	private static ThreadFactory factory = new ThreadFactory() {
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			return t;
		}
	};

	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 24,
			TimeUnit.HOURS, workQueue, factory);

	public static synchronized CrawlersTaskPool sharedInstance() {
		synchronized (CrawlersTaskPool.class) {
			if (instance == null) {
				instance = new CrawlersTaskPool();
			}
			return instance;
		}
	}

	public void doTask(ICrawlersTask task) {
		executor.execute(task);
	}

	public void doRun(Runnable task) {
		executor.execute(task);
	}

	public ThreadPoolExecutor getExecutor() {
		return executor;
	}
	
	public static void main(String[] args){
		System.out.println(10%11);
	}
}