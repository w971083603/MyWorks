package com.platform.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 迟到，未打卡
 */
@Data
@TableName(value = "m_user_event_reduce")
public class EventReduceEntity implements Serializable {
    @TableId(type = IdType.AUTO,value = "id")
    private Long id;

    @TableField(value = "days")
    private Integer days;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "record_time")
    private Timestamp recordTime;

    @TableField(value = "type")
    private String type;

    @TableField(value = "hours")
    private BigDecimal hours;

    @TableField(value = "remarks")
    private String remarks;

    @TableField(value = "create_time")
    private Timestamp createTime;

}
