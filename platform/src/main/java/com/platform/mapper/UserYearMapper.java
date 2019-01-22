package com.platform.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.platform.entity.UserYearEntity;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/4/28.
 */
public interface UserYearMapper extends BaseMapper<UserYearEntity> {

    List<UserYearEntity> selectList(Map<String, String> params);


}
