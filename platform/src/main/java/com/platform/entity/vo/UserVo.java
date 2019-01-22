package com.platform.entity.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 用户实体
 */
@Data
public class UserVo {

    private Long id;

    private String loginNumber;

    private String password;

    private String name;

    private String phone;

    private Long roleId;

    private String department;

    private String station;

    private Timestamp createTime;

    private Timestamp lastTime;

    private Long userStatus;

    private Long addressId;

    private String addressName;


}
