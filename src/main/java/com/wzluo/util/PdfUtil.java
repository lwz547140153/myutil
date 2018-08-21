package com.wzluo.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

public class PdfUtil {

    /**
     * 将inputStream中的pdf文件流添加水印text后输出到outputStream
     * @param inputStream pdf文件流
     * @param outputStream 输出流
     * @param text
     */
    public static void waterMark(InputStream inputStream,
            OutputStream outputStream, String text ) {
        try {
            PdfReader reader = new PdfReader(inputStream);
            Field f = PdfReader.class.getDeclaredField("ownerPasswordUsed");
            f.setAccessible(true);
            f.set(reader, Boolean.TRUE);
            PdfStamper stamper = new PdfStamper(reader, outputStream);
                 /* BaseFont base = BaseFont.createFont(
                          "C:/WINDOWS/Fonts/SIMSUN.TTC,1", "Identity-H", true);*/
            int total = reader.getNumberOfPages();
            for (int i = 1; i <= total; i++) {
                w(reader.getPageSize(i),stamper.getOverContent(i),text);
            }
            stamper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void w(Rectangle size,PdfContentByte canvas,String text){
        try {
            float x1 = size.getWidth()/2;
            float y1 = size.getHeight()/2;
            float xflag = size.getWidth()/7;
            float yflag = size.getHeight()/7;
            float[] x = {x1 - 3*xflag, x1 - xflag, x1 + xflag};
            float[] y = {y1 - 3*yflag, y1 - yflag, y1 + yflag, y1 + 3*yflag};
            for (int i = 0; i < x.length; i++) {
                for (int j = 0; j < y.length; j++) {
                    ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                            new Phrase(text, new Font(BaseFont.createFont( "STSongStd-Light" ,"UniGB-UCS2-H",false ), 15, Font.NORMAL, BaseColor.LIGHT_GRAY)),
                            y[i],
                            y[j],
                            45);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        InputStream inputStream = new FileInputStream("C:/Users/Administrator/Desktop/java基础教程_Java.pdf");
        OutputStream outputStream = new FileOutputStream("C:/Users/Administrator/Desktop/watermark9.pdf");
        PdfUtil.waterMark(inputStream,outputStream,"罗万竹 13012345678");
    }

}
