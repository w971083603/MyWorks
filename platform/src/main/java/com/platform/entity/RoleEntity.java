package com.platform.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 角色
 */
@Data
@TableName(value = "m_roles")
public class RoleEntity {

    @TableId(type = IdType.AUTO, value = "id")
    private Long roleId;

    @TableField(value = "role_name")
    private String roleName;

    @TableField(value = "create_date")
    private Timestamp createDate;


    @TableField(value = "update_date")
    private Timestamp updateDate;

    @TableField(value = "is_delete")
    private Integer isDelete;


}
