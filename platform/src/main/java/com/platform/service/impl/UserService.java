package com.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.commons.utils.Util;
import com.platform.entity.UserEntity;
import com.platform.entity.vo.UserVo;
import com.platform.mapper.UserMapper;
import com.platform.result.DatatablesResult;
import com.platform.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/17.
 *
 */

@Service
@Transactional
public class  UserService extends ServiceImpl<UserMapper,UserEntity> implements IUserService {

    @Autowired
    private  UserMapper userMapper;

    @Override
    public Long getUserId(String loginNumber){
        UserEntity userEntity = userMapper.findByLoginNumber(loginNumber);
        if(userEntity == null){
            return null;
        }
        return userEntity.getId();
    }


    @Override
    public DatatablesResult<UserVo> selectUserList(JSONObject params, Integer page, Integer rows, Integer draw) {
        PageHelper.startPage(page, rows);
        Map<String,String> whereMap = Util.toHashMap(params);
        List<UserVo> userlistVos = this.userMapper.selectUserList(whereMap);
        PageInfo<UserVo> pageInfo = new PageInfo<UserVo>(userlistVos);
        DatatablesResult pageResult = new DatatablesResult<UserVo>();
        pageResult.setData(userlistVos);
        pageResult.setDraw(draw);
        pageResult.setRecordsTotal(((int)pageInfo.getTotal())==0?1:(int)pageInfo.getTotal());
        pageResult.setRecordsFiltered(pageResult.getRecordsTotal());
        return pageResult;
    }
}
