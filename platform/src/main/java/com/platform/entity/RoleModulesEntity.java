package com.platform.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * 角色关联菜单
 */
@Data
@TableName(value = "m_role_modules")
public class RoleModulesEntity {

    @TableId(type = IdType.AUTO, value = "id")
    private Long id;

    @TableField(value = "role_id")
    private Long roleId;


    @TableField(value = "module_id")
    private Long moduleId;


}
