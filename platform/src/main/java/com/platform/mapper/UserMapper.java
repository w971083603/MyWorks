package com.platform.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.platform.entity.UserEntity;
import com.platform.entity.vo.UserVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/4/28.
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    Integer countUserByLoginNumber(String loginNumber);

    List<UserVo> selectUserList(Map<String, String> params);

    UserEntity findByLoginNumber(String loginNumber);


    @Select("select id , name from m_sys_user where role_id != 1 order by name desc")
    List<UserEntity> findAll();

}
