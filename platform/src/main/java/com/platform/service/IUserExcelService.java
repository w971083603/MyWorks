package com.platform.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.platform.entity.UserDayEntity;
import com.platform.entity.UserExcelEntity;
import com.platform.result.DatatablesResult;
import org.springframework.web.multipart.MultipartFile;


/**
 * Created by Administrator on 2017/8/17.
 *
 */

public interface IUserExcelService extends IService<UserExcelEntity> {

     DatatablesResult<UserDayEntity> selectList(JSONObject params, Integer page, Integer rows, Integer draw);

    void redExcel(MultipartFile file) throws Exception;
}
