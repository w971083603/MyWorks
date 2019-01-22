package com.platform.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.platform.commons.utils.PageData;
import com.platform.entity.UserDayEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/4/28.
 */
public interface UserDayMapper extends BaseMapper<UserDayEntity> {

    List<UserDayEntity> selectList(Map<String, String> params);

    List<PageData> countAll(PageData pd);

    List<PageData> countAllEvery(PageData pd);

    List<PageData> countAllForDay(PageData pd);


    @Select("select id from m_user_day where user_id=#{userId} and days = #{days}")
    Long selectUserDayByDaysAndUserId(@Param(value = "days") int days, @Param(value = "userId") long userId);


    @Select("select ifnull(sum(overtime_work_hours-break_time),0) from m_user_day where user_id=#{userId} and record_time <= #{endDay}")
    Double sumLessTime(@Param(value = "endDay") String endDay, @Param(value = "userId") long userId);

}
