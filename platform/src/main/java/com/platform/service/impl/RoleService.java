package com.platform.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.platform.entity.RoleEntity;
import com.platform.mapper.RoleMapper;
import com.platform.service.IRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleService extends ServiceImpl<RoleMapper, RoleEntity> implements IRoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public Map<String, Set<String>> selectModulesMapByUserId(String roleIds) {
		Map<String, Set<String>> resourceMap = new HashMap<String, Set<String>>();
		Set<String> modSet = new HashSet<String>();
		Set<String> roles = new HashSet<String>();
		String[] str=String.valueOf(roleIds).split(",");
		for (int i = 0; i < str.length; i++) {
			if(!str[i].trim().equals("")){
				Long roleId=Long.parseLong(str[i]);
				List<Map<Long, String>> resourceList = roleMapper.selectModulesListByRoleId(roleId);
				if (resourceList != null) {
					for (Map<Long, String> map : resourceList) {
						if (!StringUtils.isEmpty(map.get("modulstr"))) {
							modSet.add(map.get("modulstr"));
						}
					}
				}
				RoleEntity role = roleMapper.selectById(roleId);
				if (role != null) {
					roles.add(String.valueOf(role.getRoleId()));
				}
			}
		}
		resourceMap.put("mods", modSet);
		resourceMap.put("roles", roles);
		return resourceMap;
	}


}
