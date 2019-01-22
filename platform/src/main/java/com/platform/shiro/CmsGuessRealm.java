package com.platform.shiro;




import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.platform.commons.utils.Util;
import com.platform.entity.UserEntity;
import com.platform.mapper.UserMapper;
import com.platform.service.IRoleService;
import com.platform.service.IUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


import java.sql.Timestamp;
import java.util.*;

public class CmsGuessRealm extends AuthorizingRealm {

	@Autowired
	private IUserService userService;
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private IRoleService roleService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo( PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
 		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(shiroUser.getRoles());
		info.addStringPermissions(shiroUser.getModulesSet());
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo( AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
 		UserEntity userEntity=userMapper.findByLoginNumber(token.getUsername());
		if(userEntity == null)throw new UnknownAccountException();// 不存在
		if (userEntity.getUserStatus() == 1) throw new DisabledAccountException();// 账号未启用
		// 读取用户的url和角色
		Map<String, Set<String>> resourceMap = roleService.selectModulesMapByUserId(userEntity.getRoleId()+"");
		System.out.println("权限id【"+resourceMap.get("roles")+"】集合");
		System.out.println("菜单权限【"+resourceMap.get("mods")+"】集合");
		ShiroUser shiroUser = new ShiroUser(userEntity.getId(), userEntity.getLoginNumber(),userEntity.getName(), resourceMap.get("mods"));
		shiroUser.setRoles( resourceMap.get("roles"));
		shiroUser.setRolesstr(String.valueOf(userEntity.getRoleId()));
		shiroUser.setPhone(userEntity.getPhone());
		shiroUser.setAddressId(userEntity.getAddressId());//所在地区编号
		//根据权限设置ID;
 		String password= userEntity.getPassword();
		//更新登录时间
		try {
			//获取登录密码
			String nowlogin_password=String.valueOf(token.getPassword());
			if(Util.getMD5Str(nowlogin_password).equals(password)){
				UserEntity userEntity1=new UserEntity();
				userEntity1.setLastTime(new Timestamp(System.currentTimeMillis()));
				EntityWrapper wrapper = new EntityWrapper();
				wrapper.where("id = {0}", userEntity.getId());
				userService.update(userEntity1, wrapper);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new SimpleAuthenticationInfo(shiroUser,password.toCharArray(), getName());
	}

	@Override
	public void onLogout(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		removeUserCache(shiroUser);
	}

	/**
	 * 清除用户缓存
	 *
	 * @param shiroUser
	 */
	public void removeUserCache(ShiroUser shiroUser) {
		removeUserCache(shiroUser.getLoginName());
		removeUserCache(shiroUser.getPhone());
	}

	/**
	 * 清除用户缓存
	 *
	 * @param loginName
	 */
	public void removeUserCache(String loginName) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection();
		principals.add(loginName, super.getName());
		super.clearCachedAuthenticationInfo(principals);
	}

}
