package com.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.commons.utils.Util;
import com.platform.entity.EventReduceEntity;
import com.platform.entity.vo.EventReduceVo;
import com.platform.mapper.EventReduceMapper;
import com.platform.result.DatatablesResult;
import com.platform.service.IEventReduceService;
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
public class EventReduceService extends ServiceImpl<EventReduceMapper,EventReduceEntity> implements IEventReduceService {

    @Autowired
    private  EventReduceMapper eventReduceMapper;

    @Override
    public DatatablesResult<EventReduceVo> selectList(JSONObject params, Integer page, Integer rows, Integer draw) {
        PageHelper.startPage(page, rows);
        Map<String,String> whereMap = Util.toHashMap(params);
        List<EventReduceVo> voList = this.eventReduceMapper.selectListAll(whereMap);
        PageInfo<EventReduceVo> pageInfo = new PageInfo<EventReduceVo>(voList);
        DatatablesResult pageResult = new DatatablesResult<EventReduceVo>();
        pageResult.setData(voList);
        pageResult.setDraw(draw);
        pageResult.setRecordsTotal(((int)pageInfo.getTotal())==0?1:(int)pageInfo.getTotal());
        pageResult.setRecordsFiltered(pageResult.getRecordsTotal());
        return pageResult;
    }



}
