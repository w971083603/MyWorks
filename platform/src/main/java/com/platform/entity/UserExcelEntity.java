package com.platform.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 导入excel
 */
@Data
@TableName(value = "m_user_excel")
public class UserExcelEntity implements Serializable {

    @TableId(type = IdType.AUTO,value = "id")
    private Long id;

    @TableField(value = "organization_name")
    private String  organizationName;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "login_number")
    private String loginNumber;

    @TableField(value = "name")
    private String name;

    @TableField(value = "days")
    private String days;

    @TableField(value = "week")
    private String week;

    @TableField(value = "one_time")
    private String oneTime;

    @TableField(value = "two_time")
    private String twoTime;

    @TableField(value = "three_time")
    private String threeTime;

    @TableField(value = "four_time")
    private String fourTime;

    @TableField(value = "create_time")
    private Timestamp createTime;

}
