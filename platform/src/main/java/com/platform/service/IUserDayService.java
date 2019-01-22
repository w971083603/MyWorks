package com.platform.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.platform.commons.utils.PageData;
import com.platform.entity.UserDayEntity;
import com.platform.result.DatatablesResult;

import java.text.ParseException;


/**
 * Created by Administrator on 2017/8/17.
 */

public interface IUserDayService extends IService<UserDayEntity> {

    DatatablesResult<UserDayEntity> selectList(JSONObject params, Integer page, Integer rows, Integer draw);

    int setDay(String recordTime, Long userId, PageData pd) throws ParseException;


}
