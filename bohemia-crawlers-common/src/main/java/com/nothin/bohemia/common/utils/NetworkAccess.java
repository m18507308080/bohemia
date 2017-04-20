package com.nothin.bohemia.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.w3c.dom.Element;

import com.nothin.bohemia.common.exceptions.NetWorkConnectException;

public class NetworkAccess {

	private static NetworkAccess networkAccess = new NetworkAccess();

	private static String[] ipProxyArrayLibrary;

	private static Random ipArrayRandom;

	private NetworkAccess() {
	};

	static {
		InputStream in;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			in = new FileInputStream(new File(Constant.DEFAULT_PROXY));
			Element root = XmlUtils.getRootElementFromStream(in);
			List<Element> elementList = XmlUtils.getElements(root, "proxy");
			for (Element element : elementList) {
				stringBuffer.append(element.getAttribute("host") + ":"
						+ element.getAttribute("port"));
				stringBuffer.append("\r\n");
			}
			ipProxyArrayLibrary = stringBuffer.toString().split("\r\n");
			ipArrayRandom = new Random(ipProxyArrayLibrary.length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static NetworkAccess getInstance() {
		return networkAccess;
	}

	public String readRandomIP() {
		return ipProxyArrayLibrary[ipArrayRandom
				.nextInt(ipProxyArrayLibrary.length)];
	}

	public String getHttpDocument(String uri) throws Exception {
		String httpDocument = null;
		try {
			httpDocument = getHttpRequest(uri, null, null, Constant.USERAGENT,false);
		} catch (Exception e) {
			throw e;
		}
		return httpDocument;
	}

	public String getHttpDocumentByRandomProxy(String uri) throws Exception {
		String httpDocument = null;
		String[] hostPort = readRandomIP().split(":");
		try {
			httpDocument = getHttpRequest(uri, hostPort[0],Integer.parseInt(hostPort[1]), Constant.USERAGENT, true);
		} catch (Exception e) {
			throw e;
		}
		return httpDocument;
	}

	public String getHttpRequest(String uri, String host, Integer port,
			String userAgent, boolean proxyFalg)
			throws NetWorkConnectException, IOException {

		StringBuffer result = new StringBuffer();

		/* 1 生成 HttpClinet 对象并设置参数 */
		HttpClient httpClient = new HttpClient();

		if (proxyFalg) {
			/* 代理的主机 */
			ProxyHost proxy = new ProxyHost(host, port);
			
			/* 使用代理 */
			httpClient.getHostConfiguration().setProxyHost(proxy);
		}

		/* 添加 userAgent */
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,
				userAgent);

		/* 链接超时 */
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(70000);

		/* 读取超时 */
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(70000);

		/* 生成 GetMethod 对象并设置参数 */
		GetMethod getMethod = new GetMethod(uri);

		/* 请求超时 */
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 70000);
		getMethod.getParams().setParameter(
				HttpMethodParams.HEAD_BODY_CHECK_TIMEOUT, 70000);

		/* 设置 get 请求超时为 2 秒 */
		getMethod.getParams().setSoTimeout(2000);

		/* 设置请求重试处理，用的是默认的重试处理：请求三次 */
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());

		/* 执行 HTTP GET 请求 */
		InputStream response = null;
		BufferedReader in = null;
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new NetWorkConnectException("读取地址返回代码错误");
			} else {
				response = getMethod.getResponseBodyAsStream();
				in = new BufferedReader(new InputStreamReader(response, "GBK"));
				String line;
				while ((line = in.readLine()) != null) {
					result.append("\n").append(line);
				}
			}
		} catch (HttpException e) {
			throw new NetWorkConnectException("网络连接异常");
		} catch (IOException e) {
			throw new IOException("页面内容读取异常");
		} finally {
			/* 释放连接 */
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
		return result.toString();
	}

}
