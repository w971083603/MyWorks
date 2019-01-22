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
 * 用户的事件统计
 */
@Data
@TableName(value = "m_user_day")
public class UserDayEntity implements Serializable {

    @TableId(type = IdType.AUTO,value = "id")
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "days")
    private Integer days;

    @TableField(value = "working_hours")
    private BigDecimal workingHours;

    @TableField(value = "overtime_work_hours")
    private BigDecimal overtimeWorkHours;

    @TableField(value = "break_time")
    private BigDecimal breakTime;

    @TableField(value = "less_time")
    private BigDecimal lessTime;

    @TableField(value = "late_time")
    private BigDecimal lateTime;

    @TableField(value = "not_hit_time")
    private BigDecimal notHitTime;

    @TableField(value = "record_time")
    private String recordTime;

    @TableField(value = "last_time")
    private Timestamp lastTime;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String loginNumber;
}
