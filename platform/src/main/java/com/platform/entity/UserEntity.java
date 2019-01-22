package com.platform.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 用户实体
 */
@Data
@TableName(value = "m_sys_user")
public class UserEntity {

    @TableId(type = IdType.AUTO,value = "id")
    private Long id;

    @TableField(value = "login_number")
    private String loginNumber;

    @TableField(value = "password")
    private String password;

    @TableField(value = "name")
    private String name;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "role_id")
    private Long roleId;

    @TableField(value = "department")
    private String department;

    @TableField(value = "station")
    private String station;

    @TableField(value = "create_time")
    private Timestamp createTime;

    @TableField(value = "last_time")
    private Timestamp lastTime;

    @TableField(value = "user_status")
    private Long userStatus;

    @TableField(value = "address_id")
    private Long addressId;


}
