package com.wzluo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
    /**
     *
     * httpPost
     *
     * @param message
     *            发送报文
     * @return 返回报文
     * @throws Exception
     */
    public static String sendMessage(String url, String message) throws IOException {
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer("");
        String strMessage = "";
        try {
            // System.out.println("发送的报文：" + message);
            // 连接报文的地址
            URL uploadServlet = new URL(url);
            HttpURLConnection servletConnection = (HttpURLConnection) uploadServlet.openConnection();

            // 设置连接参数
            servletConnection.setRequestMethod("POST");
            servletConnection.setDoOutput(true);
            servletConnection.setDoInput(true);
            servletConnection.setAllowUserInteraction(true);

            // 开启流，写入XML数据
            OutputStream output = servletConnection.getOutputStream();
            output.write(message.getBytes());
            output.flush();
            output.close();

            // 获取返回的数据
            InputStream inputStream = servletConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((strMessage = reader.readLine()) != null) {
                buffer.append(strMessage);
            }
            // 关闭连接
            servletConnection.disconnect();
        } catch (ConnectException e) {
            throw e;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return buffer.toString();
    }

    public static void main(String args[]) {
        try {
			/*Map<String, String> map = new HashMap<String, String>();
			map.put("Content-Type", "application/json");
			map.put("userToken", "8d228a046584494aafa91a61715cb738");
			aa.readContentFromGet(
					"http://47.90.16.40:18080/ebox/api/v1/express/query?page=0&maxCount=1&expressType=COURIER_STORE&takeUserPhone=18996939693",
					map);*/

            HttpUtil.sendMessage("http://222.177.210.150:8081/message/sms","send_type=1&phone=15723135730&message=测试&sysName=1001");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
