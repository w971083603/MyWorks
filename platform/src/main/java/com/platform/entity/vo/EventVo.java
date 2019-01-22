package com.platform.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 事件列表展示
 */
@Data
public class EventVo implements Serializable {
    private Long id;
    private String type;
    private String startDay;
    private String startTime;
    private String endDay;
    private String endTime;
    private BigDecimal hours;
    private String reason;
    private String examineReason;
    private Integer status;
    private Timestamp createTime;
    private Timestamp examineTime;


    private String loginNumber;
    private String name;
}
