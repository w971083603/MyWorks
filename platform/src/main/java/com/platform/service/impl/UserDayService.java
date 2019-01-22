package com.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.commons.utils.DateUtil;
import com.platform.commons.utils.PageData;
import com.platform.commons.utils.Util;
import com.platform.entity.UserDayEntity;
import com.platform.mapper.UserDayMapper;
import com.platform.result.DatatablesResult;
import com.platform.service.IUserDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/17.
 */

@Service
@Transactional
public class UserDayService extends ServiceImpl<UserDayMapper, UserDayEntity> implements IUserDayService {

    @Autowired
    private UserDayMapper userDayMapper;


    @Override
    public DatatablesResult<UserDayEntity> selectList(JSONObject params, Integer page, Integer rows, Integer draw) {
        PageHelper.startPage(page, rows);
        Map<String, String> whereMap = Util.toHashMap(params);
        List<UserDayEntity> voList = this.userDayMapper.selectList(whereMap);
        PageInfo<UserDayEntity> pageInfo = new PageInfo<UserDayEntity>(voList);
        DatatablesResult pageResult = new DatatablesResult<UserDayEntity>();
        for (UserDayEntity userDayEntity : voList) {
            userDayEntity.setLessTime(BigDecimal.valueOf(userDayMapper.sumLessTime(userDayEntity.getRecordTime(), userDayEntity.getUserId())));
        }
        pageResult.setData(voList);
        pageResult.setDraw(draw);
        pageResult.setRecordsTotal(((int) pageInfo.getTotal()) == 0 ? 1 : (int) pageInfo.getTotal());
        pageResult.setRecordsFiltered(pageResult.getRecordsTotal());
        return pageResult;
    }


    //每天为单位存入数据
    @Transactional
    @Override
    public int setDay(String recordTime, Long userId, PageData pd) throws ParseException {
        int days = Integer.parseInt(recordTime.replaceAll("-", ""));
        //判断是否已经存在
        Long dayId = userDayMapper.selectUserDayByDaysAndUserId(days, userId);
        UserDayEntity userDayEntity = new UserDayEntity();
        //不存在数据时，数据初始化
        if(dayId == null){
            userDayEntity.setDays(days);
            userDayEntity.setUserId(userId);
            //判断是否周六周日
            if (DateUtil.isWeekend(recordTime)) {
                userDayEntity.setWorkingHours(BigDecimal.ZERO);
            }else{
                userDayEntity.setWorkingHours(BigDecimal.valueOf(7.5));
            }
            userDayEntity.setBreakTime(BigDecimal.ZERO);
            userDayEntity.setOvertimeWorkHours(BigDecimal.ZERO);
            userDayEntity.setNotHitTime(BigDecimal.ZERO);
            userDayEntity.setLateTime(BigDecimal.ZERO);
            userDayEntity.setLessTime(BigDecimal.ZERO);
            userDayEntity.setLastTime(new Timestamp(System.currentTimeMillis()));
            userDayEntity.setRecordTime(recordTime);
        } else {
            userDayEntity = userDayMapper.selectById(dayId);
        }
        //判断是否有调休时间H单位
        if(pd.containsKey("breakTime")){
            double actionWorkingTime = 7.5-pd.getDouble("breakTime");
            userDayEntity.setWorkingHours(BigDecimal.valueOf(actionWorkingTime));
            userDayEntity.setBreakTime(BigDecimal.valueOf(pd.getDouble("breakTime")));
        }
        //判断是否加班
        if(pd.containsKey("overtimeWorkHours")){
            userDayEntity.setOvertimeWorkHours(BigDecimal.valueOf(pd.getDouble("overtimeWorkHours")));
        }
        //判断是否迟到--上班情况下才发生
        if(pd.containsKey("latetime")){
            double actionWorkingTime = 7.5-pd.getDouble("latetime");
            userDayEntity.setWorkingHours(BigDecimal.valueOf(actionWorkingTime));
            userDayEntity.setLateTime(BigDecimal.valueOf(pd.getDouble("latetime")));
        }
        //判断是否未打卡--上班情况下才发生
        if(pd.containsKey("notHitTime")){
            double actionWorkingTime = 7.5-pd.getDouble("notHitTime");
            userDayEntity.setWorkingHours(BigDecimal.valueOf(actionWorkingTime));
            userDayEntity.setNotHitTime(BigDecimal.valueOf(pd.getDouble("notHitTime")));
        }
        int n = 0 ;
        if(dayId == null){
            n = userDayMapper.insert(userDayEntity);
        }else{
//            userDayEntity.setId(dayId);
            n = userDayMapper.updateById(userDayEntity);
        }
        return n;
    }

}
