package com.platform.controller;

import com.platform.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


public abstract class BaseController {


	/**
	 * 获取当前登录用户对象
	 *
	 * @return {ShiroUser}
	 */
	public ShiroUser getShiroUser() {
		return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 获取当前登录用户id
	 *
	 * @return {Long}
	 */
	public Long getUserId() {
		return this.getShiroUser().getId();
	}

	/**
	 * 获取当前登录用户名
	 *
	 * @return {String}
	 */
	public String getStaffName() {
		return this.getShiroUser().getName();
	}

	protected Map getRequestMap(HttpServletRequest request) {
		Map map = new HashMap();
		Map<String, String[]> tmp = request.getParameterMap();
		if (tmp != null) {
			for (String key : tmp.keySet())
			{
				String[] values = (String[])tmp.get(key);
				if (values.length == 1)
				{
					String val = values[0].trim();
					if (("null".equals(val)) || ("undefined".equals(val))) {
						val = "";
					}
					map.put(key, val);
				}
				else
				{
					map.put(key, values);
				}
			}
		}
		return map;
	}

}
