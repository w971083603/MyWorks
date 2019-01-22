package com.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.platform.commons.utils.ResponseWrapper;
import com.platform.entity.EventEntity;
import com.platform.result.DatatablesResult;
import com.platform.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 * Description: *_*
 */

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private IEventService eventService;

    /**
     * 新增
     *
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity save(EventEntity eventEntity) {
        ResponseWrapper result;
        try {
            String dayStr = eventEntity.getStartDay();
            int days = Integer.parseInt(dayStr.replaceAll("-", ""));
            //判断是否添加过
            EntityWrapper<EventEntity> userwrapper = new EntityWrapper<EventEntity>();
            userwrapper.and("user_id = {0}", eventEntity.getUserId())
                    .and("days = {0}",days)
                    .and("type = {0}",eventEntity.getType())
                    .and("status != 2 ");
            List<EventEntity> entities = eventService.selectList(userwrapper);
            if(entities.size() > 0){
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("当天记录已添加");
            }
            eventEntity.setDays(days);
            eventEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            boolean n = eventService.insert(eventEntity);
            result = ResponseWrapper.succeed(n);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }


    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity delete(Long id) {
        ResponseWrapper result;
        try {
            boolean n = eventService.deleteById(id);
            result = ResponseWrapper.succeed(n);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 更改审核状态
     *
     * @return
     */
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public ResponseEntity updatePassword(Long eventId, Integer status, String examineReason) {
        ResponseWrapper result;
        try {
            int n = eventService.changeStatus(eventId, status, examineReason);
            if(n == 0){
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("审核失败，请稍后再试");
            }
            result = ResponseWrapper.succeed(n);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 后台审核列表
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
            DatatablesResult datatablesResult = this.eventService.selectListAll(params, page, rows, draw);
            return ResponseEntity.ok(datatablesResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


}
