package com.platform.commons.interceptor;


import com.platform.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 自定义拦截器，判断请求是否有权限
 * Created by Administrator on 2017/8/11.
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        System.out.println(path);
        if (path.contains("login")
                || path.contains("address/listAll")
                || path.contains("register")) {
            return true;
        }
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/platform/login";
        Object obj = SecurityUtils.getSubject().getPrincipal();
        if (obj == null) {
            response.sendRedirect(basePath);
            return false;
        }
        ShiroUser shiroUser = (ShiroUser) obj;
        String loginName = shiroUser.getLoginName();
        // 创建session
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        ServletContext application=session.getServletContext();
        Map<String, String> loginMap = (Map<String, String>)application.getAttribute("loginMap");
        if(loginMap==null){
            response.sendRedirect(basePath);
            return false;
        }
        for(String key:loginMap.keySet()) {
            if (loginName.equals(key)) {
                if(!sessionId.equals(loginMap.get(key))) {
                    session.setAttribute("message","当前账号已在他处登录");
                    response.sendRedirect(basePath);
                    return false;
                }
            }
        }
        return true;
    }
}