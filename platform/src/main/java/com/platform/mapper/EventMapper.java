package com.platform.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.platform.entity.EventEntity;
import com.platform.entity.vo.EventVo;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/4/28.
 */
public interface EventMapper extends BaseMapper<EventEntity> {

    List<EventVo> selectListAll(Map<String, String> params);


}
