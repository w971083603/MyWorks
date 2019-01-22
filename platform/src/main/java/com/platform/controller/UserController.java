package com.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.platform.commons.utils.ResponseWrapper;
import com.platform.commons.utils.Util;
import com.platform.entity.*;
import com.platform.mapper.UserMapper;
import com.platform.result.DatatablesResult;
import com.platform.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 * Description: *_*
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册
     *
     * @param userEntity
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(UserEntity userEntity) {
        ResponseWrapper result;
        try {
            //判断工号是否已经注册
            if (userMapper.countUserByLoginNumber(userEntity.getLoginNumber()) > 0)
                return ResponseEntity.status(-1).body("当前工号已经存在");
            //注册
            userEntity.setPassword(Util.getMD5Str(userEntity.getPassword()));
            boolean n = userService.insert(userEntity);
            result = ResponseWrapper.succeed(n);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }


    /**
     * 更改密码/重置密码
     *
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public ResponseEntity updatePassword(Long userId, String oldPassword, String newPassword) {
        ResponseWrapper result;
        try {
            UserEntity userEntity = userMapper.selectById(userId);
            //判断工号是否已经注册
            if (userEntity == null) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("当前用户不存在");
            if (!userEntity.getPassword().equals(Util.getMD5Str(oldPassword))) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("旧密码输入错误");
            userEntity.setPassword(Util.getMD5Str(newPassword));
            boolean n = userService.updateById(userEntity);
            result = ResponseWrapper.succeed(n);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }


    /**
     * 重置密码
     *
     * @return
     */
    @RequestMapping(value = "/restartPassword", method = RequestMethod.POST)
    public ResponseEntity restartPassword(Long userId){
        ResponseWrapper result;
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userId);
            userEntity.setPassword(Util.getMD5Str("123456"));
            boolean n = userService.updateById(userEntity);
            result = ResponseWrapper.succeed(n);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 后台用户列表
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
            DatatablesResult datatablesResult = this.userService.selectUserList(params, page, rows, draw);
            return ResponseEntity.ok(datatablesResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    /**
     * 获取全部会员
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public ResponseEntity findAll() {
        ResponseWrapper result;
        try {
            List<UserEntity> userEntities= userMapper.findAll();
            result = ResponseWrapper.succeed(userEntities);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

}
