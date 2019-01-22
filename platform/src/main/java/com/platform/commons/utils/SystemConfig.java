package com.platform.commons.utils;

import lombok.Data;

/**
 * 系统参数
 *
 * Created by Administrator on 2017/6/30.
 */
@Data
public class SystemConfig {

    /**
     * 获取系统参数配置的实例
     * @return
     */
    public static SystemConfig getInstance(){
        SystemConfig config = (SystemConfig)SpringContextUtil.getBean("systemConfig");
        return config;
    }

    /**
     * 接口地址（CMS）
     */
    private String URL_BASE_CMS;

}
