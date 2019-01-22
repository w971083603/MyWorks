package com.platform.service;


import com.baomidou.mybatisplus.service.IService;
import com.platform.entity.RoleEntity;

import java.util.Map;
import java.util.Set;

;


public interface IRoleService extends IService<RoleEntity> {

    Map<String, Set<String>> selectModulesMapByUserId(String roleIds);
 }
