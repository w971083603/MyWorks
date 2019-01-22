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
 * 事件列表展示
 */
@Data
@TableName(value = "m_user_event")
public class EventEntity implements Serializable {
    @TableId(type = IdType.AUTO,value = "id")
    private Long id;

    @TableField(value = "days")
    private Integer days;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "type")
    private String type;

    @TableField(value = "start_day")
    private String startDay;

    @TableField(value = "start_time")
    private String startTime;

    @TableField(value = "end_day")
    private String endDay;

    @TableField(value = "end_time")
    private String endTime;

    @TableField(value = "hours")
    private BigDecimal hours;

    @TableField(value = "reason")
    private String reason;

    @TableField(value = "examine_reason")
    private String examineReason;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "create_time")
    private Timestamp createTime;

    @TableField(value = "examine_time")
    private Timestamp examineTime;
}
