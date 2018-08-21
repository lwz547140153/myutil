package com.wzluo.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import sun.misc.BASE64Encoder;

public enum QRCodeUtil {
    //变种单例模式
    INSTANCE;

    private static BASE64Encoder encoder = new BASE64Encoder();

    /**
     * 生成代有info的二维码图片
     * @param info
     * @param height
     * @param width
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage transformQRCode(String info,int height,int width)throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(info,
                BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
//        MatrixToImageConfig config =new MatrixToImageConfig(new Color(30,172,252).getRGB(),-1);
//        MatrixToImageConfig config =new MatrixToImageConfig(new Color(-14766852,true).getRGB(),-1);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }


    /**
     * 生成带datainfo的二维码图片
     * @param data
     * @param height
     * @param width
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage transformQRCode(Map<String,Object> data,int height,int width)throws WriterException, IOException{
       String str = "";
       Set<String> keySet = data.keySet();
       for(String key:keySet){
           str+=key+"="+data.get(key)+"&";
       }
       if(!str.isEmpty()){
           str=str.substring(0,str.length()-1);
       }
       return transformQRCode(str,height,width);
    }

    /**
     * 图片转dataUrl
     * @param bufferedImage
     * @return
     */
    public static String image2DataUrl(BufferedImage bufferedImage)throws IOException{
        return byteArray2DataUrl(image2ByteArray(bufferedImage));
    }

    /**
     * bytearray转dataUrl
     * @param bytes
     * @return
     */
    public static String byteArray2DataUrl(byte[] bytes){
        return "data:image/png;base64,"+encoder.encode(bytes);
    }

    /**
     * 图片转bytearray
     * @param bufferedImage
     * @return
     * @throws IOException
     */
    public static byte[] image2ByteArray (BufferedImage bufferedImage)throws IOException{
        ByteArrayOutputStream  bos = new ByteArrayOutputStream();
        try{
            ImageIO.write(bufferedImage,"png",bos);
        }finally {
            bos.close();
        }
        return bos.toByteArray();
    }

    /**
     * 图片存为文件
     * @param bufferedImage
     * @param name
     * @return
     * @throws IOException
     */
    public static File image2File(BufferedImage bufferedImage,String name)throws IOException{
        File file = new File(name);
        ImageIO.write(bufferedImage,"png",file);
        return file;
    }

    public static void main(String[] args) throws Exception{
        File file = image2File(transformQRCode("abc",550,550),"./zxing883.png");
        System.out.println(new Color(30,172,252).getRGB());
    }
}
