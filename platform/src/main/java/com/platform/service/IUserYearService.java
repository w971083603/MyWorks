package com.platform.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.platform.entity.UserYearEntity;
import com.platform.result.DatatablesResult;


/**
 * Created by Administrator on 2017/8/17.
 *
 */

public interface IUserYearService extends IService<UserYearEntity> {

     DatatablesResult<UserYearEntity> selectList(JSONObject params, Integer page, Integer rows, Integer draw);

}
