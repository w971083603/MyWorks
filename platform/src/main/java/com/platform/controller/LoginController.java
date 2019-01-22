package com.platform.controller;

import com.platform.entity.UserEntity;
import com.platform.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 登录/注册/管理
 *
 * @author wuyudong
 */
@RestController
public class LoginController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    CacheManager cacheManager;

    /**
     * post登录方法
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> loginPost(UserEntity user, HttpSession session) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //开始验证账户密码
            Subject sybject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginNumber(), user.getPassword().toCharArray());
            token.setRememberMe(true);
            //开始登录
            sybject.login(token);
            result.put("msg", "");
            //将登录账号缓存起来
            String sessionId = session.getId();
            ServletContext application = session.getServletContext();
            Map<String, String> loginMap = new HashMap<>();
            loginMap.put(user.getLoginNumber(), sessionId);
            application.setAttribute("loginMap", loginMap);
        } catch (UnknownAccountException e) {
            result.put("msg", "账号不存在");
        } catch (DisabledAccountException e) {
            result.put("msg", "账号未启用");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            result.put("msg", "密码错误");
        } catch (AuthenticationException e) {
            // 其他错误，比如锁定，如果想单独处理请单独catch处理
            e.printStackTrace();
            result.put("msg", "未知错误,请联系管理员");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("msg", "未知错误,请联系管理员");
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<Boolean> logout(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        ServletContext application = session.getServletContext();
        application.removeAttribute("loginMap");
        return ResponseEntity.ok(true);
    }


}
