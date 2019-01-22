package com.platform.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.platform.entity.AddressEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/4/28.
 */
public interface AddressMapper extends BaseMapper<AddressEntity> {

    List<AddressEntity> selectList(Map<String, String> params);

    @Select("select id from m_address where name = #{name} and is_delete = 0 limit 1")
    Long countIdByName(@Param(value = "name") String name);

}
