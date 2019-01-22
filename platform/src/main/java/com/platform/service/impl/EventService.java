package com.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.commons.utils.DateUtil;
import com.platform.commons.utils.PageData;
import com.platform.commons.utils.Util;
import com.platform.entity.EventEntity;
import com.platform.entity.vo.EventVo;
import com.platform.mapper.EventMapper;
import com.platform.result.DatatablesResult;
import com.platform.service.IEventService;
import com.platform.service.IUserDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/17.
 */

@Service
@Transactional
public class EventService extends ServiceImpl<EventMapper, EventEntity> implements IEventService {

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private IUserDayService userDayService;

    @Override
    public DatatablesResult<EventVo> selectListAll(JSONObject params, Integer page, Integer rows, Integer draw) {
        PageHelper.startPage(page, rows);
        Map<String, String> whereMap = Util.toHashMap(params);
        List<EventVo> voList = this.eventMapper.selectListAll(whereMap);
        PageInfo<EventVo> pageInfo = new PageInfo<EventVo>(voList);
        DatatablesResult pageResult = new DatatablesResult<EventVo>();
        pageResult.setData(voList);
        pageResult.setDraw(draw);
        pageResult.setRecordsTotal(((int) pageInfo.getTotal()) == 0 ? 1 : (int) pageInfo.getTotal());
        pageResult.setRecordsFiltered(pageResult.getRecordsTotal());
        return pageResult;
    }

    @Override
    public int changeStatus(Long eventId, Integer status, String examineReason) throws ParseException {
        EventEntity eventEntity = eventMapper.selectById(eventId);
        //审核通过才处理
        if(status == 1){
            String startDay = eventEntity.getStartDay();
            String type = eventEntity.getType();
            double hours = eventEntity.getHours().doubleValue();
            PageData pd = new PageData();
            //判断是什么类型：加班,调休
            if (type.equals("加班")) {
                pd.put("overtimeWorkHours", hours);
                int n = userDayService.setDay(startDay, eventEntity.getUserId(), pd);
                if (n == 0) {
                    return n;
                }
            } else {
                int i = 0;//当天
                while (hours > 0) {
                    System.out.println(hours);
                    double lessHours = hours - 7.5;
                    if (lessHours > 0) {
                        System.out.println("天：" + DateUtil.getAfterDayDate(startDay,i+"") +"时间" + 7.5);
                        pd.put("breakTime", 7.5);
                        int n = userDayService.setDay(DateUtil.getAfterDayDate(startDay,i+""), eventEntity.getUserId(), pd);
                        if (n == 0) {
                            return n;
                        }
                    } else {
                        System.out.println("天：" + DateUtil.getAfterDayDate(startDay,i+"") +"时间" + hours);
                        pd.put("breakTime", hours);
                        int n = userDayService.setDay(DateUtil.getAfterDayDate(startDay,i+""), eventEntity.getUserId(), pd);
                        if (n == 0) {
                            return n;
                        }
                    }
                    hours = lessHours;
                    i++;
                }
            }
        }
        //审核
        eventEntity.setStatus(status);
        eventEntity.setExamineReason(examineReason);
        eventEntity.setExamineTime(new Timestamp(System.currentTimeMillis()));
        int n = eventMapper.updateById(eventEntity);
        return n;
    }


    public static void main(String[] args) {
        String startDay = DateUtil.getDay();
        double hours = 22.5;
        int i = 0;//当天
        while (hours > 0) {
            System.out.println(hours);
            double lessHours = hours - 7.5;
            if (lessHours > 0) {
                System.out.println("天：" + DateUtil.getAfterDayDate(startDay,i+"") +"时间" + 7.5);
            } else {
                System.out.println("天：" + DateUtil.getAfterDayDate(startDay,i+"") +"时间" + hours);
            }
            hours = lessHours;
            i++;
        }
    }
}
