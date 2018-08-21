package com.wzluo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageUtil {

    /**
     * 检测文件流内容是否为图像(bmp/gif/jpg/png)
     * @param iis
     * @return
     */
    public static boolean sniff(ImageInputStream iis){
        boolean flags = true;
        try {
            // get all currently registered readers that recognize the image format
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) {
                flags = false;
            }
        }catch (Exception e){
            flags = false;
        }finally {
            if(iis!=null){
                try{
                    iis.close();
                }catch (Exception e){
                    //ignore
                }
            }
        }
        return flags;
    }

    /**
     * 检测文件内容是否为图像
     * @param file
     * @return
     */
    public static boolean sniff(File file){
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            return sniff(iis);
        }catch (Exception e){
            return false;
        }
    }
    /**
     * 检测指定路径的文件内容是否为图像
     * @param file
     * @return
     */
    public static boolean sniff(String file){
        try {
            return sniff(new File(file));
        }catch (Exception e){
            return false;
        }
    }
    /**
     * 检测指定路径的文件流内容是否为图像
     * @param is
     * @return
     */
    public static boolean sniff(InputStream is){
        try {
            return ImageIO.read(is)!=null;
        }catch (Exception e){
            return false;
        }
    }

    public static void main(String[] args) throws Exception{
        String file = "C:\\Users\\Administrator\\Desktop\\a.txt";
        FileInputStream fis = new FileInputStream(new File(file));
        System.out.println(sniff(fis));
    }
}
