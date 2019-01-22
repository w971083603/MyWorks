package com.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.platform.commons.utils.*;
import com.platform.entity.UserDayEntity;
import com.platform.mapper.UserDayMapper;
import com.platform.result.DatatablesResult;
import com.platform.service.*;
import com.platform.shiro.ShiroUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/8.
 * Description: *_*
 */

@Controller
@RequestMapping("/api/count")
public class CountController extends BaseController {

    @Autowired
    private IUserYearService userYearService;
    @Autowired
    private IUserDayService userDayService;
    @Autowired
    private UserDayMapper userDayMapper;
    @Autowired
    private IUserExcelService userExcelService;


    /**
     * 会员每天统计列表
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/dataGridDay", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<DatatablesResult> dataGridDay(@RequestBody JSONObject params) {
        int rows = params.getIntValue("length");
        int start = params.getIntValue("start");
        int draw = params.getIntValue("draw");
        int page = (start / rows) + 1;
        try {
            DatatablesResult datatablesResult = this.userDayService.selectList(params, page, rows, draw);
            return ResponseEntity.ok(datatablesResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    /**
     * 会员总统计列表
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/dataGridYear", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<DatatablesResult> dataGridYear(@RequestBody JSONObject params) {
        int rows = params.getIntValue("length");
        int start = params.getIntValue("start");
        int draw = params.getIntValue("draw");
        int page = (start / rows) + 1;
        try {
            DatatablesResult datatablesResult = this.userYearService.selectList(params, page, rows, draw);
            return ResponseEntity.ok(datatablesResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * excel列表
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/dataGridExcel", method = RequestMethod.POST)
    public ResponseEntity<DatatablesResult> dataGridExcel(@RequestBody JSONObject params) {
        int rows = params.getIntValue("length");
        int start = params.getIntValue("start");
        int draw = params.getIntValue("draw");
        int page = (start / rows) + 1;
        try {
            DatatablesResult datatablesResult = this.userExcelService.selectList(params, page, rows, draw);
            return ResponseEntity.ok(datatablesResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    @RequestMapping(value = "/improtExcel", method = {RequestMethod.POST})
    @ResponseBody
    public Object ImprotExcel(@RequestParam(value = "uploadFile") MultipartFile file) {
        try {
            userExcelService.redExcel(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 更改时长
     *
     * @return
     */
    @RequestMapping(value = "/updateDayById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity updateDayById(Long id, Double workingHours,
                                        Double overtimeWorkHours, Double breakTime, Double timeOfWord,
                                        Double annualHoliday
    ) {
        ResponseWrapper result;
        try {
            UserDayEntity userDayEntity = new UserDayEntity();
            userDayEntity.setId(id);
            userDayEntity.setLastTime(new Timestamp(System.currentTimeMillis()));
            userDayEntity.setWorkingHours(BigDecimal.valueOf(workingHours));
            userDayEntity.setOvertimeWorkHours(BigDecimal.valueOf(overtimeWorkHours));
            userDayEntity.setBreakTime(BigDecimal.valueOf(breakTime));
            boolean n = userDayService.updateById(userDayEntity);
            result = ResponseWrapper.succeed(n);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }


    /*
     * 导出到excel
	 * @return
	 */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel(String endMonth) {
        ModelAndView mv = new ModelAndView();
        ShiroUser shiroUser = this.getShiroUser();
        PageData pd = new PageData();
        pd.put("endMonth", endMonth);
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> titles = new ArrayList<String>();
            titles.add("时间");    //1
            titles.add("人员");    //2
            titles.add("上班工时");    //3
            titles.add("加班工时");    //4
            titles.add("调休工时");    //5
            titles.add("剩余工时");    //6
            titles.add("迟到扣除工时");    //7
            titles.add("未打卡扣除工时");    //8
            dataMap.put("titles", titles);
            pd.put("addressId", shiroUser.getAddressId());
            List<PageData> varOList = userDayMapper.countAll(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for (int i = 0; i < varOList.size(); i++) {
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("recordTime"));    //1
                vpd.put("var2", varOList.get(i).getString("name"));    //2
                vpd.put("var3", varOList.get(i).getBigDecimal("workingHours").toString());    //3
                vpd.put("var4", varOList.get(i).getBigDecimal("overtimeWorkHours").toString());    //4
                vpd.put("var5", varOList.get(i).getBigDecimal("breakTime").toString());    //5
                vpd.put("var6", varOList.get(i).getBigDecimal("lessTime").toString());    //6
                vpd.put("var7", varOList.get(i).getBigDecimal("lateTime").toString());    //7
                vpd.put("var8", varOList.get(i).getBigDecimal("notHitTime").toString());    //7
                varList.add(vpd);
            }
            dataMap.put("varList", varList);
            ObjectExcelView erv = new ObjectExcelView();
            mv = new ModelAndView(erv, dataMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }


    /*
     * 导出到excel
	 * @return
	 */
    @RequestMapping(value = "/excelAll")
    public ModelAndView excelAll(String endDay) {
        ModelAndView mv = new ModelAndView();
        ShiroUser shiroUser = this.getShiroUser();
        PageData pd = new PageData();
        pd.put("endDay", endDay);
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> titles = new ArrayList<String>();
            titles.add("人员");    //2
            titles.add("上班工时");    //3
            titles.add("加班工时");    //4
            titles.add("调休工时");    //5
            titles.add("剩余工时");    //6
            titles.add("迟到扣除工时");    //7
            titles.add("未打卡扣除工时");    //8
            titles.add("统计到时间");    //9
            dataMap.put("titles", titles);
            pd.put("addressId", shiroUser.getAddressId());
            List<PageData> varOList = userDayMapper.countAllEvery(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for (int i = 0; i < varOList.size(); i++) {
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("name"));    //2
                vpd.put("var2", varOList.get(i).getBigDecimal("workingHours").toString());    //3
                vpd.put("var3", varOList.get(i).getBigDecimal("overtimeWorkHours").toString());    //4
                vpd.put("var4", varOList.get(i).getBigDecimal("breakTime").toString());    //5
                vpd.put("var5", varOList.get(i).getBigDecimal("lessTime").toString());    //6
                vpd.put("var6", varOList.get(i).getBigDecimal("lateTime").toString());    //7
                vpd.put("var7", varOList.get(i).getBigDecimal("notHitTime").toString());    //7
                vpd.put("var8", StringUtils.isEmpty(endDay) ? "" : endDay);    //7
                varList.add(vpd);
            }
            dataMap.put("varList", varList);
            ObjectExcelView erv = new ObjectExcelView();
            mv = new ModelAndView(erv, dataMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }


    /*
	 * 导出到excel 日
	 * @return
	 */
    @RequestMapping(value = "/excelDay")
    public ModelAndView excelDay() {
        ModelAndView mv = new ModelAndView();
        ShiroUser shiroUser = this.getShiroUser();
        PageData pd = new PageData();
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> titles = new ArrayList<String>();
            titles.add("时间");    //1
            titles.add("人员");    //2
            titles.add("上班工时");    //3
            titles.add("加班工时");    //4
            titles.add("调休工时");    //5
            titles.add("迟到扣除工时");    //7
            titles.add("未打卡扣除工时");    //8
            dataMap.put("titles", titles);
            pd.put("addressId", shiroUser.getAddressId());
            List<PageData> varOList = userDayMapper.countAllForDay(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for (int i = 0; i < varOList.size(); i++) {
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("recordTime"));    //1
                vpd.put("var2", varOList.get(i).getString("name"));    //2
                vpd.put("var3", varOList.get(i).getBigDecimal("workingHours").toString());    //3
                vpd.put("var4", varOList.get(i).getBigDecimal("overtimeWorkHours").toString());    //4
                vpd.put("var5", varOList.get(i).getBigDecimal("breakTime").toString());    //5
                vpd.put("var6", varOList.get(i).getBigDecimal("lateTime").toString());    //7
                vpd.put("var7", varOList.get(i).getBigDecimal("notHitTime").toString());    //7
                varList.add(vpd);
            }
            dataMap.put("varList", varList);
            ObjectExcelView erv = new ObjectExcelView();
            mv = new ModelAndView(erv, dataMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }


}
