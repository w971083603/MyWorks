package com.platform.commons.utils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class PageData extends HashMap implements Map{
	
	private static final long serialVersionUID = 1L;
	
	Map map = null;
	HttpServletRequest request;
	
	public PageData(HttpServletRequest request) {
		this.request = request;
		try {
			request.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}
 		Map properties = request.getParameterMap();
		Map returnMap = new HashMap(); 
		Iterator entries = properties.entrySet().iterator(); 
		Entry entry;
		String name = "";  
		String value = "";  
		while (entries.hasNext()) {
			entry = (Entry) entries.next();
			name = (String) entry.getKey(); 
			Object valueObj = entry.getValue(); 
			//判断是否为加密字段
			if(null == valueObj){
				value = "";
			}else if(valueObj instanceof String[]){
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){
					try {
						values[i]=ToSBC(values[i]);
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(e.toString());
					}
					value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1);
			}else{
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		map = returnMap;
	}
	
	public PageData() {
		map = new HashMap();
	}
	
	/**
     * 半角转全角
     * @param input String.
     * @return 全角字符串.
     */
    public static String ToSBC(String input) {
             int n=input.indexOf("<");
             int m=input.lastIndexOf(">");
             if(n>0){
            	  input=input.substring(0, n);
             }
             //吧单引号或是双引号替换成空格
             if(input.contains("\'")){
            	 input= input.replaceAll("'", "");
             }
             if(input.contains("\"")){
            	 input= input.replaceAll("\"", "");
             }
//             if(input.contains("\\")){
//            	 input= input.replaceAll("\\", "");
//             }
//             if(input.contains("/")){
//            	 input= input.replaceAll("/", "");
//             }
             if(input.contains("<")){
            	 input=input.replaceAll("<", "");
             }
             if(input.contains(">")){
            	 input= input.replaceAll(">", "");
             }
             return input;
    }
	
    public static void main(String[] args) {
		//System.out.println(ToSBC("%3E%22%27%3E%3Cscript%3Ealert(%27XSS%27)%3C/script%3E"));
	}
    
	@Override
	public Object get(Object key) {
		Object obj = null;
		if(map.get(key) instanceof Object[]) {
			Object[] arr = (Object[])map.get(key);
			obj = request == null ? arr:(request.getParameter((String)key) == null ? arr:arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}
	
	public String getString(Object key) {
		return (String)get(key);
	}
	public BigDecimal getBigDecimal(Object key) {
		return new BigDecimal(String.valueOf(get(key)));
	}
	public double getDouble(Object key) {
		return (double) get(key);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}
	
	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return map.containsValue(value);
	}

	@Override
	public Set entrySet() {
		// TODO Auto-generated method stub
		return map.entrySet();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return map.isEmpty();
	}

	@Override
	public Set keySet() {
		// TODO Auto-generated method stub
		return map.keySet();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void putAll(Map t) {
		// TODO Auto-generated method stub
		map.putAll(t);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return map.size();
	}

	@Override
	public Collection values() {
		// TODO Auto-generated method stub
		return map.values();
	}
	
}
