package com.h5.weibo.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.dom4j.xpp.ProxyXmlStartTag;

import com.alibaba.fastjson.JSON;
import com.h5.weibo.ApplicationConstant;
import com.h5.weibo.model.User;
import com.h5.weibo.utils.StringUtil;
import com.h5.weibo.utils.WebUtil;

public class BaseAction {

	// [start] 获取请求中的参数,添加值
	/**
	 * 取request中的参数
	 * 
	 * @return
	 */
	protected String getStr(String key,String def) {
		String value = getValue(key);
		if(value == null)
			return def;
		
		return escape(value);
	}

	/**
	 * 取request中的参数数组
	 */
	protected String[] getValues(String key) {
		String[] strs = getRequest().getParameterValues(key);
		if (strs != null) {
			for (int i = 0; i < strs.length; i++) {
				strs[i] = escape(strs[i]);
			}
		}
		return strs;
	}

	/**
	 * 取整数值
	 * 
	 * @param key
	 * @return
	 */
	protected int getI(String key,int def) {
		String v = this.getValue(key);
		if (StringUtil.isEmpty(v))
			return def;

		try {
			return Integer.valueOf(v);
		} catch (NumberFormatException e) {
			return def;
		}
	}
	
	protected long getL(String key,long def) {
		String v = this.getValue(key);
		if (StringUtil.isEmpty(v))
			return def;

		try {
			return Long.valueOf(v);
		} catch (NumberFormatException e) {
			return def;
		}
	}


	protected short getShortValue(String key,short def) {
		String v = this.getValue(key);
		if (StringUtil.isEmpty(v))
			return def;
		try {
			return Short.parseShort(v);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	/**
	 * 通过反射直接从request中获取到bean对象
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
	protected <T> T getBeanFromRequest(Class beanClass) {
		return WebUtil.getBeanFromRequest(beanClass, getRequest());
	}

	/**
	 * 把值添加到请求中
	 */
	protected void add(String key, Object value) {
		getRequest().setAttribute(key, value);
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}
	
	private String getValue(String key){
		   return getRequest().getParameter(key);
	}
	
	// [end]

	protected void renderText(String text) {
		PrintWriter writer;
		try {
			writer = getResponse().getWriter();
			writer.write(text);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void renderJson(Object out) {
		renderText(JSON.toJSONString(out));
	}
	
	protected User getCurrentUser() {
		return (User) getSession().getAttribute(ApplicationConstant.SESSION_USER);
	}
	
	/*
	 * 跳转到其他地址
	 */
	protected void redirect(String url) {
		try {
			getResponse().sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 过滤脚本，防止脚本注入
	 */
	private String escape(String html) {

		if (html == null)
			return null;

		String result = html.replace("<", "").replace(">", "");

		return result;
	}
}
