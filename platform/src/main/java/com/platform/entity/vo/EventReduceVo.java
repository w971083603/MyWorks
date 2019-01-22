package com.platform.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 迟到，未打卡
 */
@Data
public class EventReduceVo implements Serializable {
    private Long id;

    private Integer userId;

    private Timestamp recordTime;

    private String type;

    private BigDecimal hours;

    private String remarks;

    private Timestamp createTime;

    private String name;

    private String loginNumber;


}
