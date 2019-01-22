package com.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.platform.commons.utils.PageData;
import com.platform.commons.utils.ResponseWrapper;
import com.platform.entity.EventReduceEntity;
import com.platform.result.DatatablesResult;
import com.platform.service.IEventReduceService;
import com.platform.service.IUserDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 * Description: *_*
 */

@RestController
@RequestMapping("/api/event_reduce")
public class EventReduceController {

    @Autowired
    private IEventReduceService eventReduceService;
    @Autowired
    private IUserDayService userDayService;

    /**
     * 新增
     *
      * @return
      */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity save(EventReduceEntity eventEntity) {
        ResponseWrapper result;
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dayStr = sdf.format(eventEntity.getRecordTime());  ;
            int days=Integer.parseInt(dayStr.replaceAll("-",""));
            //判断是否添加过
            EntityWrapper<EventReduceEntity> userwrapper = new EntityWrapper<EventReduceEntity>();
            userwrapper.and("user_id = {0}", eventEntity.getUserId())
                    .and("days = {0}",days)
                    .and("type = {0}",eventEntity.getType());
            List<EventReduceEntity> eventReduceEntityList = eventReduceService.selectList(userwrapper);
            if(eventReduceEntityList.size() > 0){
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("当天记录已添加");
            }
            eventEntity.setDays(days);
            eventEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            //更新day时间
            PageData pd = new PageData();
            if(eventEntity.getType().equals("迟到")){
                pd.put("latetime",eventEntity.getHours().doubleValue());
            }else{
                pd.put("notHitTime",eventEntity.getHours().doubleValue());
            }
            int n = userDayService.setDay(dayStr,eventEntity.getUserId(),pd);
            if(n == 0){
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("新增失败，请稍后再试");
            }
            //开始新增记录
            boolean flag = eventReduceService.insert(eventEntity);
            if(!flag){
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("新增失败，请稍后再试");
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
            DatatablesResult datatablesResult = this.eventReduceService.selectList(params, page, rows, draw);
            return ResponseEntity.ok(datatablesResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


}
