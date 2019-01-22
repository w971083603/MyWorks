package com.platform.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.platform.entity.EventReduceEntity;
import com.platform.entity.vo.EventReduceVo;
import com.platform.result.DatatablesResult;


/**
 * Created by Administrator on 2017/8/17.
 *
 */

public interface IEventReduceService extends IService<EventReduceEntity> {

     DatatablesResult<EventReduceVo> selectList(JSONObject params, Integer page, Integer rows, Integer draw);

}
