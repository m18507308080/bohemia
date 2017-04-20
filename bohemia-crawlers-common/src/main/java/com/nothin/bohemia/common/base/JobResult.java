package com.nothin.bohemia.common.base;

import java.net.URL;

/*
 * 标准的返回结构集
 */
public class JobResult {
	public static final String JOBERROR_PATH = "1";
	public static final String JOBERROR_URL = "2";
	public static final String JOBOK_PATH = "3";
	public static final String JOBOK_URL = "4";
	public static final String JOBPARTOK_PATH = "5";
	public static final String JOBPARTOK_URL = "6";
	//脚本执行完成后生成的新的子节点
    private String[] childNodePath;
    //脚本执行出错的节点
    private String[] errorPath;
    //脚本执行出错的URL
    private URL[] childNodeUrl; 
    //脚本执行出错的URL
    private URL[] errorUrl; 
    //脚本执行的状态
    private String jobStatus;
    
    public String[] getChildNodePath() {
		return childNodePath;
	}
	public void setChildNodePath(String[] childNodePath) {
		this.childNodePath = childNodePath;
	}
	public String[] getErrorPath() {
		return errorPath;
	}
	public void setErrorPath(String[] errorPath) {
		this.errorPath = errorPath;
	}
	public URL[] getChildNodeUrl() {
		return childNodeUrl;
	}
	public void setChildNodeUrl(URL[] childNodeUrl) {
		this.childNodeUrl = childNodeUrl;
	}
	public URL[] getErrorUrl() {
		return errorUrl;
	}
	public void setErrorUrl(URL[] errorUrl) {
		this.errorUrl = errorUrl;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	
}