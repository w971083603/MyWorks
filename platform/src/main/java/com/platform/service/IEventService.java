package com.platform.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.platform.entity.EventEntity;
import com.platform.entity.vo.EventVo;
import com.platform.result.DatatablesResult;

import java.text.ParseException;


/**
 * Created by Administrator on 2017/8/17.
 *
 */

public interface IEventService extends IService<EventEntity> {

     DatatablesResult<EventVo> selectListAll(JSONObject params, Integer page, Integer rows, Integer draw);

     int changeStatus(Long eventId, Integer status, String examineReason) throws ParseException;

}
