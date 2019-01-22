package com.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.commons.utils.Util;
import com.platform.entity.UserYearEntity;
import com.platform.mapper.UserYearMapper;
import com.platform.result.DatatablesResult;
import com.platform.service.IUserYearService;
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
public class UserYearService extends ServiceImpl<UserYearMapper,UserYearEntity> implements IUserYearService {

    @Autowired
    private  UserYearMapper userYearMapper;

    @Override
    public DatatablesResult<UserYearEntity> selectList(JSONObject params, Integer page, Integer rows, Integer draw) {
        PageHelper.startPage(page, rows);
        Map<String,String> whereMap = Util.toHashMap(params);
        List<UserYearEntity> voList = this.userYearMapper.selectList(whereMap);
        PageInfo<UserYearEntity> pageInfo = new PageInfo<UserYearEntity>(voList);
        DatatablesResult pageResult = new DatatablesResult<UserYearEntity>();
        pageResult.setData(voList);
        pageResult.setDraw(draw);
        pageResult.setRecordsTotal(((int)pageInfo.getTotal())==0?1:(int)pageInfo.getTotal());
        pageResult.setRecordsFiltered(pageResult.getRecordsTotal());
        return pageResult;
    }



}
