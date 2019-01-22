package com.platform.quartz;

import com.platform.commons.utils.DateUtil;
import com.platform.commons.utils.PageData;
import com.platform.entity.UserEntity;
import com.platform.mapper.UserMapper;
import com.platform.service.IUserDayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;


@Slf4j
@Component
public class UserDayJob {

    @Autowired
    private IUserDayService userDayService;

    @Autowired
    private UserMapper userMapper;

    @Scheduled(cron = "0 1 0 * * ?")
    public void notifyJob() {
        String dayStr = DateUtil.getDay();
        List<UserEntity> userEntityList = userMapper.findAll();
        for (UserEntity userEntity : userEntityList) {
            try {
                userDayService.setDay(dayStr, userEntity.getId(), new PageData());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /*@Scheduled(cron = "0 * * * * ?")
    public void testJob() {
        String dayStr = DateUtil.getDay();
        for (int i = 0; i < 39; i++) {
            int j = i;
            String day = DateUtil.getAfterDayDate(dayStr, String.valueOf(-j));
            List<UserEntity> userEntityList = userMapper.findAll();
            for (UserEntity userEntity : userEntityList) {
                try {
                    userDayService.setDay(day, userEntity.getId(), new PageData());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

    }*/

}
