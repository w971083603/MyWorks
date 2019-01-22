package com.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.platform.commons.utils.ResponseWrapper;
import com.platform.entity.AddressEntity;
import com.platform.mapper.AddressMapper;
import com.platform.result.DatatablesResult;
import com.platform.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 * Description: *_*
 */

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @Autowired
    private AddressMapper addressMapper;


    /**
     * 新增
     * * @return
     *
     * @throws IOException
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity add(AddressEntity addressEntity) {
        ResponseWrapper result;
        try {
            //判断工号是否已经注册
            if (addressMapper.countIdByName(addressEntity.getName()) != null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("当前地区已经存在");
            }
            addressEntity.setIsDelete(0);
            boolean n = addressService.insert(addressEntity);
            result = ResponseWrapper.succeed(n);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 修改
     * * @return
     *
     * @throws IOException
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity update(AddressEntity addressEntity) {
        ResponseWrapper result;
        try {
            //判断工号是否已经注册
            Long beforeId = addressMapper.countIdByName(addressEntity.getName());
            if (beforeId != null && beforeId != addressEntity.getId()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("当前地区已经存在");
            }
            boolean n = addressService.updateById(addressEntity);
            result = ResponseWrapper.succeed(n);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }


    /**
     * 列表
     * * @return
     *
     * @throws IOException
     */
    @RequestMapping(value = "/listAll", method = RequestMethod.POST)
    public ResponseEntity listAll() {
        ResponseWrapper result;
        try {
            EntityWrapper<AddressEntity> entityEntityWrapper = new EntityWrapper<>();
            entityEntityWrapper.where("is_delete=?", 0);
            List<AddressEntity> list = addressService.selectList(entityEntityWrapper);
            result = ResponseWrapper.succeed(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 后台地址列表
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/dataGrid", method = RequestMethod.POST)
    public ResponseEntity<DatatablesResult> dataGrid(@RequestBody JSONObject params) {
        int rows = params.getIntValue("length");
        int start = params.getIntValue("start");
        int draw = params.getIntValue("draw");
        int page = (start / rows) + 1;
        try {
            DatatablesResult datatablesResult = this.addressService.selectUserList(params, page, rows, draw);
            return ResponseEntity.ok(datatablesResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


}
