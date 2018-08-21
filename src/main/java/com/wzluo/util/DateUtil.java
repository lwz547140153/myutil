package com.wzluo.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {


    /**
     * 计算年假天数
     * @param workDate yyyy-MM-dd
     * @return
     * @throws Exception
     */
    public static float getAvailable(String  workDate) throws Exception{
        return getAvailable(new SimpleDateFormat("yyyy-MM-dd").parse(workDate));
    }
    /**
     * 计算年假天数
     * @param workDate yyyy-MM-dd
     * @return
     * @throws Exception
     */
    public static float getAvailable(Date workDate){
        float availableDay=0;
        List<Integer> totalWorkAge=getWorkAge(workDate);//计算累计的总工龄
        int year = totalWorkAge.get(0);
        int month = totalWorkAge.get(1);
        //case1:累计工龄未满1年
        if(year==0){
            availableDay=0;
        }else if(year==1){//1年
            availableDay=(5.0f*month)/12;// 5*工龄月数/12
        }else if(year<10){//2-9年
            availableDay=5;
        }else if(year==10){//10年
            availableDay=((5.0f*(12-month))/12)+((10.0f*month)/12);
        }else if(year<20){//11-19年
            availableDay=10;
        }else if(year==20){//20年
            availableDay=((10.0f*(12-month))/12)+((15.0f*month)/12);
        }else if(year>20){//大于20年
            availableDay=15;
        }
        return availableDay;
    }
    private static List<Integer> getWorkAge(Date d){
        Date date=new Date();//获得当前日期
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);//获得年份
        int month = 12;
        cal.setTime(d);
        int year2 = cal.get(Calendar.YEAR);
        int month2 = cal.get(Calendar.MONTH);
        int t = year - year2;//得到年差
        int m = month -month2;//得到月差
        if(m<0){
            t = t - 1;
            m = 12 + m;
        }

        return Arrays.asList(t,m);
    }

    /**
     * 计算当前可用年假天数
     * @param availableDay
     * @param reachDate 入职时间
     * @return
     * @throws Exception
     */
    public static float getReal(float availableDay,Date reachDate) throws Exception{
        Calendar cal = Calendar.getInstance();
        cal.setTime(reachDate);
        int reachYear=cal.get(Calendar.YEAR);
        int reachMonth = cal.get(Calendar.MONTH);
        cal.setTime(new Date());
        int currentYear = cal.get(Calendar.YEAR);
        //int currentMonth = cal.get(Calendar.MONTH);
        if(reachYear==currentYear){//今年新进员工
            //扣除已经过去的月份
            availableDay = availableDay*(12-reachMonth)/12;
        }
        return availableDay;
    }
}
