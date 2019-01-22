package com.platform.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户的事件统计
 */
@Data
@TableName(value = "m_address")
public class AddressEntity implements Serializable {

    @TableId(type = IdType.AUTO,value = "id")
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "is_delete")
    private Integer isDelete;
}
