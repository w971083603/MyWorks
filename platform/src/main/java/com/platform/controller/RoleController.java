package com.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.platform.commons.utils.ResponseWrapper;
import com.platform.entity.RoleModulesEntity;
import com.platform.service.IRoleModulesService;
import com.platform.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * 角色管理
 * @author wuyudong
 *
 */
@Controller
@RequestMapping("/api/role")
public class RoleController {



	@Autowired
	private IRoleService roleService;

	@Autowired
	private IRoleModulesService roleModulesService;


	/**
	 * 获取权限相关的菜单
	 * @param roleId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/system/findModulesById",method = RequestMethod.POST)
	public ResponseEntity findModulesById(String roleId) throws IOException{
		ResponseWrapper result;
		try {
			RoleModulesEntity rm=new RoleModulesEntity();
			rm.setRoleId(Long.parseLong(roleId));
			EntityWrapper<RoleModulesEntity> wrapper = new EntityWrapper<RoleModulesEntity>(rm);
			List<RoleModulesEntity> relist=roleModulesService.selectList(wrapper);
			String modulesstr="";
			for(RoleModulesEntity roleModulesEntity : relist){
				if(roleModulesEntity.getModuleId().toString().length() == 1){
					modulesstr+="0"+roleModulesEntity.getModuleId()+",";
				}else{
					modulesstr+=roleModulesEntity.getModuleId()+",";
				}
			}
			result = ResponseWrapper.succeed(modulesstr);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(-1).body(e.getMessage());
		}
		return ResponseEntity.ok(result);
	}


}
