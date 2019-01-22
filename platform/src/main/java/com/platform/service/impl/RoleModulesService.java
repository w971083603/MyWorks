package com.platform.service.impl;



import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.platform.entity.RoleModulesEntity;
import com.platform.mapper.RoleModulesMapper;
import com.platform.service.IRoleModulesService;
import org.springframework.stereotype.Service;

@Service
public class RoleModulesService extends ServiceImpl<RoleModulesMapper, RoleModulesEntity> implements IRoleModulesService {}
