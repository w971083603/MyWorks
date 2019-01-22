package com.platform.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户的事件总统计
 */
@Data
@TableName(value = "m_user_year")
public class UserYearEntity implements Serializable {
    @TableId(type = IdType.AUTO,value = "id")
    private Long id;

    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "working_houts")
    private BigDecimal workingHours;

    @TableField(value = "overtime_work_hours")
    private BigDecimal overtimeWorkHours;

    @TableField(value = "break_time")
    private BigDecimal breakTime;

    @TableField(value = "time_of_word")
    private BigDecimal timeOfWord;

    @TableField(value = "annual_holiday")
    private BigDecimal annualHoliday;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String loginNumber;

}
