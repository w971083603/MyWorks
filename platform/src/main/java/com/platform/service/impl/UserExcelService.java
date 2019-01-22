package com.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.platform.commons.utils.Util;
import com.platform.entity.UserDayEntity;
import com.platform.entity.UserExcelEntity;
import com.platform.mapper.UserExcelMapper;
import com.platform.result.DatatablesResult;
import com.platform.service.IUserExcelService;
import com.platform.service.IUserService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/17.
 */

@Service
public class UserExcelService extends ServiceImpl<UserExcelMapper, UserExcelEntity> implements IUserExcelService {

    @Autowired
    private UserExcelMapper userExcelMapper;

    @Autowired
    private IUserService userService;

    @Override
    public DatatablesResult<UserDayEntity> selectList(JSONObject params, Integer page, Integer rows, Integer draw) {
        PageHelper.startPage(page, rows);
        Map<String, String> whereMap = Util.toHashMap(params);
        List<UserExcelEntity> voList = this.userExcelMapper.selectList(whereMap);
        PageInfo<UserExcelEntity> pageInfo = new PageInfo<UserExcelEntity>(voList);
        DatatablesResult pageResult = new DatatablesResult<UserExcelEntity>();
        pageResult.setData(voList);
        pageResult.setDraw(draw);
        pageResult.setRecordsTotal(((int) pageInfo.getTotal()) == 0 ? 1 : (int) pageInfo.getTotal());
        pageResult.setRecordsFiltered(pageResult.getRecordsTotal());
        return pageResult;
    }


    /**
     * 导入excel
     *
     * @return
     */
    @Transactional
    @Override
    public void redExcel(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        HSSFWorkbook book = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = book.getSheetAt(0);
        /**
         * 通常第一行都是标题，所以从第二行开始读取数据
         */
        for (int i= 1; i < sheet.getLastRowNum() + 1; i++) {
                System.out.println("第=========" + i);
                HSSFRow row = sheet.getRow(i);
                HSSFCell organizationNameCell = row.getCell(0);
                if (organizationNameCell != null) organizationNameCell.setCellType(Cell.CELL_TYPE_STRING);
                HSSFCell loginNumberCell = row.getCell(1);
                if (loginNumberCell != null) loginNumberCell.setCellType(Cell.CELL_TYPE_STRING);
                HSSFCell nameCell = row.getCell(2);
                if (nameCell != null) nameCell.setCellType(Cell.CELL_TYPE_STRING);
                HSSFCell punchDayCell = row.getCell(3);
                if (punchDayCell != null) punchDayCell.setCellType(Cell.CELL_TYPE_STRING);
                HSSFCell weekCell = row.getCell(4);
                if (weekCell != null) weekCell.setCellType(Cell.CELL_TYPE_STRING);
                HSSFCell punchTime1Cell = row.getCell(5);
                if (punchTime1Cell != null) punchTime1Cell.setCellType(Cell.CELL_TYPE_STRING);
                HSSFCell punchTime2Cell = row.getCell(6);
                if (punchTime2Cell != null) punchTime2Cell.setCellType(Cell.CELL_TYPE_STRING);
                HSSFCell punchTime3Cell = row.getCell(7);
                if (punchTime3Cell != null) punchTime3Cell.setCellType(Cell.CELL_TYPE_STRING);
                HSSFCell punchTime4Cell = row.getCell(8);
                if (punchTime4Cell != null) punchTime4Cell.setCellType(Cell.CELL_TYPE_STRING);
                //start
                String organizationName = organizationNameCell.getStringCellValue().trim();
                String loginNumber = loginNumberCell.getStringCellValue().trim();
                if (Strings.isNullOrEmpty(loginNumber)) return;
                String name = nameCell.getStringCellValue().trim();
                Long userId = userService.getUserId(loginNumber);
                String punchDay = punchDayCell == null ? null : punchDayCell.getStringCellValue().trim();
                String week = weekCell.getStringCellValue().trim();
                String punchTime1 = punchTime1Cell == null ? null : punchTime1Cell.getStringCellValue().trim();
                String punchTime2 = punchTime2Cell == null ? null : punchTime2Cell.getStringCellValue().trim();
                String punchTime3 = punchTime3Cell == null ? null : punchTime3Cell.getStringCellValue().trim();
                String punchTime4 = punchTime4Cell == null ? null : punchTime4Cell.getStringCellValue().trim();
                //开始存储数据
                UserExcelEntity userExcelEntity = new UserExcelEntity();
                userExcelEntity.setOrganizationName(organizationName);
                userExcelEntity.setDays(punchDay);
                userExcelEntity.setUserId(userId);
                userExcelEntity.setWeek(week);
                userExcelEntity.setName(name);
                userExcelEntity.setLoginNumber(loginNumber);
                userExcelEntity.setOneTime(punchTime1);
                userExcelEntity.setTwoTime(punchTime2);
                userExcelEntity.setThreeTime(punchTime3);
                userExcelEntity.setFourTime(punchTime4);
                userExcelEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
                Long dayId = userExcelMapper.countForUserAndDays(punchDay, loginNumber);
                if (dayId != null) {
                    userExcelEntity.setId(dayId);
                    userExcelMapper.updateById(userExcelEntity);
                } else {
                    userExcelMapper.insert(userExcelEntity);
                }
        }
    }

}
