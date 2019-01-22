package com.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.commons.utils.Util;
import com.platform.entity.AddressEntity;
import com.platform.mapper.AddressMapper;
import com.platform.result.DatatablesResult;
import com.platform.service.IAddressService;
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
public class AddressService extends ServiceImpl<AddressMapper,AddressEntity> implements IAddressService {

    @Autowired
    private  AddressMapper addressMapper;

    @Override
    public DatatablesResult<AddressEntity> selectUserList(JSONObject params, Integer page, Integer rows, Integer draw) {
        PageHelper.startPage(page, rows);
        Map<String,String> whereMap = Util.toHashMap(params);
        List<AddressEntity> list = this.addressMapper.selectList(whereMap);
        PageInfo<AddressEntity> pageInfo = new PageInfo<AddressEntity>(list);
        DatatablesResult pageResult = new DatatablesResult<AddressEntity>();
        pageResult.setData(list);
        pageResult.setDraw(draw);
        pageResult.setRecordsTotal(((int)pageInfo.getTotal())==0?1:(int)pageInfo.getTotal());
        pageResult.setRecordsFiltered(pageResult.getRecordsTotal());
        return pageResult;
    }
}
