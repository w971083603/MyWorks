package com.platform.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.platform.entity.UserEntity;
import com.platform.entity.vo.UserVo;
import com.platform.result.DatatablesResult;


/**
 * Created by Administrator on 2017/8/17.
 *
 */

public interface IUserService extends IService<UserEntity> {

     Long getUserId(String loginNumber);

     DatatablesResult<UserVo> selectUserList(JSONObject params, Integer page, Integer rows, Integer draw);

}
