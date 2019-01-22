package com.platform.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.platform.entity.AddressEntity;
import com.platform.result.DatatablesResult;


/**
 * Created by Administrator on 2017/8/17.
 *
 */

public interface IAddressService extends IService<AddressEntity> {

     DatatablesResult<AddressEntity> selectUserList(JSONObject params, Integer page, Integer rows, Integer draw);

}
