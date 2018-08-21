package com.wzluo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtil {
    public static List<String> matches(String src,String reg){
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(src);
        List<String> list = new ArrayList<>();
        while (matcher.find()){
            list.add(matcher.group());
        }
        return list;
    }

    /**
     * 注意，simpleReg正则必须为不带分组的简单正则表达式，至多一个限定符，且只能是+，限定符匹配串串长不超过10字符，例如：C\d+  d+长度不超过10(正),  C\d*  （误）
     * 否则匹配时可能报错
     * 匹配 src中匹配了simpleReg以外 的内容
     * @param src
     * @param simpleReg
     * @return
     */
    public static List<String> beyondMatches(String src,String simpleReg){
        int index = simpleReg.indexOf("+");
        String preMatch = index!=-1?new StringBuilder(simpleReg).insert(index,"{1,10}").toString():simpleReg;
        return matches(src.replaceAll("^("+simpleReg+")+","").replaceAll("("+simpleReg+")("+simpleReg+")+","$1")
                ,"((?<="+preMatch+")|^)(.+?)((?=("+simpleReg+")+)|$)");
    }

    public static void main(String[] args) {
        String test = "C2C12C21caaCbC12C66C77de3a4Cfg56C78ha123C923aabC123C321";
        List<String> result = RegUtil.matches(test,"C\\d+");
        for(String m:result){
            System.out.print(m+" ");//C2 C12 C21 C12 C66 C77 C78 C923 C123 C321
        }
        System.out.println();
        System.out.println("========");
        result = RegUtil.beyondMatches(test,"C\\d+");
        for(String m:result){
            System.out.print(m+" ");//caaCb de3a4Cfg56 ha123 aab
        }
    }
}
