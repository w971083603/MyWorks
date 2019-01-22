package com.platform.commons.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Title: com.platform.commons.utils.
 * @Description：TODO
 * @Company：杭州猜一猜网络科技有限公司
 * @author： 智刚
 * @date：14:06 2018/4/18
 */
public class DateUtil {

    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private final static SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");

    private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

    private final static SimpleDateFormat sdfDay2 = new SimpleDateFormat("yyyy/MM/dd");

    private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

    private final static SimpleDateFormat sdfDays3 = new SimpleDateFormat("yyMMddHHmmss");

    private final static SimpleDateFormat sdfDayshms = new SimpleDateFormat("yyyyMMddhhmmss");

    private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final static SimpleDateFormat sdfTimeHm = new SimpleDateFormat("HH:mm");

    private final static SimpleDateFormat sdfTimeHms = new SimpleDateFormat("HHmmss");

    private final static SimpleDateFormat sdfDay3 = new SimpleDateFormat("MM.dd");

    private final static SimpleDateFormat sdfSTime = new SimpleDateFormat("yyyyMMddhhmmssSSS");

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear() {
        return sdfYear.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay() {
        return sdfDay.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDate(Date date) {
        return sdfDay.format(date);
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDate1(Date date) {
        return sdfDays.format(date);
    }


    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays() {
        return sdfDays.format(new Date());
    }

    /**
     * 获取yyMMddHHmmss格式
     *
     * @return
     */
    public static String getDays3() {
        return sdfDays3.format(new Date());
    }

    /**
     * 获取yyyyMMddhhmmss格式
     *
     * @return
     */
    public static String getDayshms() {
        return sdfDayshms.format(new Date());
    }

    /**
     * 时间差，小时
     */
    public static Double compareGetHours(String start, String end) throws Exception {
        long startM = sdfTime.parse(start).getTime();
        long endM = sdfTime.parse(end).getTime();

        double hours = (endM - startM) / (1000 * 60 * 60.0);

        return hours;
    }


    public static Boolean isWeekend(String bDate) throws ParseException {
        Date bdate = sdfDay.parse(bDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 得到  n天  之后的日期 yyyy-MM-dd
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(String beginDate, String days) {
        int daysInt = Integer.parseInt(days);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(fomatDate(beginDate));

        calendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = calendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * 格式化日期  yyyy-MM-dd
     *
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

    }
}
