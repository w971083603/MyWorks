package com.platform.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.platform.entity.EventReduceEntity;
import com.platform.entity.vo.EventReduceVo;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/4/28.
 */
public interface EventReduceMapper extends BaseMapper<EventReduceEntity> {

    List<EventReduceVo> selectListAll(Map<String, String> params);


}
