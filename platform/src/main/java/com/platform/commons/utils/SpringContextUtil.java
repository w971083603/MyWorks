package com.platform.commons.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 用来取得上下文ApplicationContext
 *
 * ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:META-INF/spring/applicationContext-common.xml");
 * 上述的代码会带来一个问题：
 *   因为它会重新装载applicationContext-common.xml并实例化上下文bean,
 *   如果有些线程配置类也是在这个配置文件中，那么会造成做相同工作的的线程会被启两次。一次是web容器初始化时启动，另一次是上述代码显示的实例化了一次。这在业务上是要避免的。
 * 解决方法：不用类似new ClassPathXmlApplicationContext()的方式，从已有的spring上下文取得已实例化的
 * Created by Administrator on 2017/6/29.
 */
public class SpringContextUtil implements ApplicationContextAware {

    // Spring应用上下文环境
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     *
     * @param applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取对象
     * 这里重写了bean方法，起主要作用
     * @param name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

}
