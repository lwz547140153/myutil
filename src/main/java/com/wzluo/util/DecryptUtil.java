package com.wzluo.util;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

/**
 * 解密工具
 */
public class DecryptUtil {
    private static Random r = new SecureRandom();
    private static char[] chars =  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890~!@#$%^&*.?".toCharArray();

    /**
     * 随机生成字符串
     * @param length
     * @return
     */
    public static final String randomPassword(int length){
        StringBuffer str = new StringBuffer();
        for(int i = 0;i<length;i++){
            str.append(chars[r.nextInt(chars.length)]);
        }
        return str.toString();
    }

    /**
     * 对static\lib\core\encrypt.js encrypt方法加密的串进行解密
     */
    public static final String decrypt(String base64){
        //解码
        String str = new String( Base64.getDecoder().decode(base64));
        int length = str.length();
        if(length==12){
            throw new IllegalArgumentException("参数错误！");
        }
        char[]chars=str.toCharArray();
        List<List<int[]>> arr=new ArrayList();
        //查找需要反转的元素
        e(0,chars.length-1,0,arr);
        //反转
        for(int i= arr.size()-1;i>=0;i--){
            List<int[]> list = arr.get(i);
            for(int[] ele:list){
                reverse(chars,ele[0],ele[1]);
            }
        }
        //分组还原
        int len = chars.length;
        int len1 = len;
        if(len%2==0){
            len--;
        }else{
            len1--;
        }
        reverse(chars,0,len-1,2);
        int i = 1;
        int j = len1-1;
        int count = (i+j)/2;
        if (count%2!=0){
            reverse(chars,i,count-2,2);
            reverse(chars,count+2,j,2);
        }else{
            reverse(chars,i,count-1,2);
            reverse(chars,count+1,j,2);
        }
        //去补位
        if(length<12){
            return new String(chars,1,length-2);
        }else{
            return new String(chars,3,length-3);
        }
    }
    /**
     * 交换chars[i]和chars[j]
     * @param chars
     * @param i
     * @param j
     */
    private static void swap(char[] chars,int i,int j){
        char temp = chars[i];
        chars[i]=chars[j];
        chars[j]=temp;
    }

    /**
     * 反转s-e之间的字符
     * @param chars
     * @param s
     * @param e
     * @param step
     */
    private static final void reverse(char[] chars,int s,int e, int step){
        while (s<e){
            swap(chars,s,e);
            s+=step;
            e-=step;
        }
    }

    private static final void reverse(char[] chars,int s,int e){
         reverse(chars,s,e,1);
    }

    public static void main(String[] args) {
        for(int i = 1;i<=100;i++){
            System.out.println(randomPassword(i));
        }
        String[] srcs = {"MeKRoGU=","Q0/ikaHikaA=","4pGiNOKRoW7ikaA=","4pGiNyvikaPikaDikaE=","4pGh4pGkS+KRok7ikaDikaM=",
                "MuKRoeKRpDnikaLikaXikaDikaM=","4pGh4pGmaeKRpOKRo+KRolrikaDikaU=","4pGh4pGkReKRpkTikaLikafikaXikaPikaA=","4pGo4pGj4pGhZOKRpuKRpOKRom3ikafikaXikaA=","4pGm4pGh4pGoZ+KRpErikaPikafikaLikalo4pGl4pGg",
                "4pGq4pGh4pGkcuKRpmnikajikafikaLikanikaDikaNG4pGl","4pGh4pGq4pGj4pGoNeKRplrikaTikanikaLikavikaDikaUz4pGn","4pGh4pGq4pGj4pGsZuKRpnfikajikanikaLikavikaTikaU14pGn4pGg",
                "4pGq4pGh4pGs4pGj4pGmSOKRqHbikaXikavikaLika3ikaTikadH4pGp4pGg","4pGu4pGh4pGm4pGj4pGsa+KRqFHikarikavikaLika3ikaTikalP4pGl4pGg4pGn",
                "4pGj4pGs4pGl4pGu4pGh4pGoRuKRqkrikabika3ikaLika/ikaTikatk4pGn4pGg4pGp","4pGu4pGh4pGj4pGl4pGweeKRqOKRquKRrErikaTikavika3ika/ikabikadu4pGg4pGi4pGp",
                "4pGj4pGs4pGu4pGw4pGl4pGoTEfikaHikarikafikaTika3ika/ikbHikabikalH4pGg4pGi4pGr","4pGj4pGw4pGy4pGo4pGl4pGhN+KRquKRrOKRrkPikaTika3ika/ikbHikabikaJt4pGn4pGp4pGr4pGg",
                "4pGw4pGj4pGl4pGn4pGy4pGu4pGqUFLikaHikazikajikaTika/ikbHikbPikabikaJ54pGp4pGr4pGt4pGg"};
        for(int i = 0;i<20;i++){
            System.out.println(decrypt(srcs[i]));
        }
    }

    /**
     * 计算 递归折半逆序 法的边界
     * @param start
     * @param end
     * @param hier
     * @param arr
     */
    public static void e(int start,int end,int hier,List<List<int[]>> arr){
        if(start>=end){
            return;
        }
        int[] se = {start,end};
        List<int[]> list = null;
        try{
            list = arr.get(hier);
        }catch (IndexOutOfBoundsException e){
            list = new ArrayList<>();
            arr.add(list);
        }
        list.add(se);
        hier++;
        int d = end-start;
        int half = d/2;
        if(d%2==0){
            half--;
        }
        e(start,start+half,hier,arr);
        e(end-half,end,hier,arr);
    }
}
