package com.platform.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.platform.entity.RoleEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/28.
 */
public interface RoleMapper extends BaseMapper<RoleEntity> {

    List<Map<Long, String>>  selectModulesListByRoleId(Long roleId);

}
