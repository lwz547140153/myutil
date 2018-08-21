package com.wzluo.sb2.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegTest {

    public static void main(String[] args) {
//        String test = "C2C12C21caaCbC12C66C77de3a4Cfg56C78ha123C923aabC123";
        //([a-zA-Z]+(?![^C]))|((?!\d+)[^C]+)|(C[^\d]+)
        //(((?!\d+)[^C]+)|(C[^\d]+)
        String test = "C66C77de3a4Cfg56C78C66ha123C923aabC123g";
        String reg = "((?<=C\\d{1,10}+)|^)(.+?)((?=(C\\d+)+)|$)";
        test = test.replaceAll("^(C\\d+)+","");//预先将打头连续的可匹配串替换成空
        test = test.replaceAll("("+"C\\d+"+")("+"C\\d+"+")+","$1");//预先将串中连续的可匹配串替换掉
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(test);
        while (matcher.find()){
            System.out.print(matcher.group()+" ");//de3a4Cfg56 ha123 aab g
        }
    }
}
