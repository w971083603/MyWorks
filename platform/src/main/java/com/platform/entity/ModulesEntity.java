package com.platform.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.List;

/**
 * 角色关联菜单
 */
@Data
@TableName(value = "m_modules")
public class ModulesEntity {

    @TableId(type = IdType.AUTO, value = "id")
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "url")
    private String url;

    @TableField(value = "pid")
    private Long pid;

    @TableField(value = "module_type")
    private Long moduleType;

    @TableField(value = "open_mode")
    private String openMode;

    @TableField(value = "description")
    private String description;


    @TableField(value = "icon")
    private String icon;


    @TableField(value = "seq")
    private Long seq;

    @TableField(value = "opened")
    private Long opened;


    @TableField(value = "status")
    private Long status;

    @TableField(exist = false)
    private List<ModulesEntity> list;

    @TableField(exist = false)
    private String moduleId;


}
