package com.platform.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.platform.entity.UserExcelEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/4/28.
 */
public interface UserExcelMapper extends BaseMapper<UserExcelEntity> {

    List<UserExcelEntity> selectList(Map<String, String> params);

    @Select("select id from m_user_excel where days=#{days} and login_number=#{loginNumebr}")
    Long countForUserAndDays(@Param(value = "days") String days, @Param(value = "loginNumebr") String loginNumebr);



    @Select("select ifnull(id,1) from m_user_excel order by id desc limit 1")
    Long getMaxId();


}
