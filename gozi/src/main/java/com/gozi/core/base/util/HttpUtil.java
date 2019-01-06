package com.gozi.core.base.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

public class HttpUtil {

    public static String getIp(){
       return HttpUtil.getRequest().getRemoteHost();
    }
    // 默认的连接延时
    private static int ConnectTimeout = 3000;

    /**
     * 设置连接延时
     *
     * @param time 延时(毫秒)
     */
    public static void setConnecttimeout(int time) {
        ConnectTimeout = time;
    }

    /**
     * 发送get请求并读取响应(默认使用utf-8进行编码)
     *
     * @param getUrl 请求url
     * @return 响应
     */
    public static String HttpGetResponse(String getUrl) {

        URLConnection urlConnection = null;
        try {
            urlConnection = getConnection(getUrl);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String responce = getResponce(urlConnection);

        return responce;
    }

    /**
     * 发送get请求并读取响应
     *
     * @param getUrl  请求url
     * @param hashMap 请求字符串
     * @return 响应
     */
    public static String HttpGetResponse(String getUrl,
                                         HashMap<String, String> hashMap) {
        return HttpGetResponse(getUrl, hashMap, "utf-8");

    }

    /**
     * @param urlConnection
     * @return
     */
    private static String getResponce(URLConnection urlConnection) {

        BufferedReader bufferedReader = null;

        try {

            InputStream inputStream = urlConnection.getInputStream();

            boolean isGzip = false;
            boolean isDeflate = false;

            if (urlConnection.getHeaderField("Content-Encoding") != null) {
                isGzip = urlConnection.getHeaderField("Content-Encoding")
                        .equals("gzip");
                isDeflate = urlConnection.getHeaderField("Content-Encoding")
                        .equals("deflate");
            }

            String line = urlConnection.getContentType();

            String charset = line == null ? null : getCharestType(line);
            InputStream inputStream2 = inputStream;
            if (isDeflate == true) {
                inputStream2 = new DeflaterInputStream(inputStream2);
            } else if (isGzip == true) {
                inputStream2 = new GZIPInputStream(inputStream2);
            }
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream2, charset == null ? Charset.defaultCharset()
                    : Charset.forName(charset));

            bufferedReader = new BufferedReader(inputStreamReader);

        } catch (IOException e) {
            System.out.println("打开URL的输出流失败");
            e.printStackTrace();
        }
        StringBuffer stringBuffer = null;
        String line;
        if (bufferedReader != null) {
            stringBuffer = new StringBuffer();
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return stringBuffer == null ? null : stringBuffer.toString();
    }

    private static URLConnection getConnection(String url) throws IOException {
        URL urls = new URL(url);
        URLConnection urlConnection = urls.openConnection();
        urlConnection.setConnectTimeout(ConnectTimeout);
        // 设置请求头 (照抄火狐的)
        urlConnection
                .setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0");
        urlConnection
                .setRequestProperty("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        urlConnection.setRequestProperty("Accept-Language",
                "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        urlConnection.setRequestProperty("Accept-Encoding", "gzip,deflate");
        urlConnection.setRequestProperty("Connection", "keep-alive");
        urlConnection.setRequestProperty("Content-type", "application/json");

        return urlConnection;
    }

    /**
     * 从头字段中解析出字符集的类型
     *
     * @param line
     * @return 字符集的类型, 或者null
     */
    private static String getCharestType(String line) {

        Pattern pattern = Pattern.compile("charset=(.+)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }

    }

    /**
     * 发送post,并接受响应
     *
     * @param url    url地址
     * @param params 需要请求的键值对
     * @return 请求结果
     */
    public static String HttpPostResponse(String url,
                                          HashMap<String, String> params) {

        StringBuffer stringBuffer = new StringBuffer();

        String string = parseHashMapToString(params);

        return HttpPostResponse(url, string);
    }
    private static String parseHashMapToString(HashMap<String, String> params) {

        StringBuffer stringBuffer = new StringBuffer();
        Set<String> set = params.keySet();
        for (String key : set) {
            try {
                stringBuffer.append(key + "="
                        + URLEncoder.encode(params.get(key), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            stringBuffer.append("&");
        }
        String string = stringBuffer.substring(0, stringBuffer.length() - 1);
        return string;
    }
    /**
     * 发送post,并接受响应
     *
     * @param url      url地址
     * @param postData post的字符串数据,需要自己组装成键值对的形式
     * @return 请求
     */
    public static String HttpPostResponse(String url, String postData) {

        try {
            URLConnection urlConnection = getConnection(url);
            urlConnection.setDoOutput(true);
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(urlConnection.getOutputStream()));
            bufferedWriter.write(postData);
            bufferedWriter.close();

            return getResponce(urlConnection);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 使用指定字符集发送消息
     *
     * @param getUrl  请求url
     * @param hashMap 请求字符串
     * @param charest 需要使用的字符集
     * @return
     */
    public static String HttpGetResponse(String getUrl,
                                         HashMap<String, String> hashMap, String charest) {
        String queryString = parseHashMapToString(hashMap);

        // 进行编码
        try {
            queryString = new String(queryString.getBytes(charest), charest);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return HttpGetResponse(getUrl + "?" + queryString);
    }
    /**
     * 获取所有请求的值
     */
    public static Map<String, String> getRequestParameters() {
        HashMap<String, String> values = new HashMap<String, String>();	
        HttpServletRequest request = HttpUtil.getRequest();
        Enumeration enums = request.getParameterNames();
        while ( enums.hasMoreElements()){
            String paramName = (String) enums.nextElement();
            String paramValue = request.getParameter(paramName);
            values.put(paramName, paramValue);
        }
        return values;
    }

    /**
     * 获取 HttpServletRequest
     */
    public static HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    /**
     * 获取 包装防Xss Sql注入的 HttpServletRequest
     * @return request
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @param param 请求参数
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, Map<String, String> param) {
        String result = "";
        BufferedReader in = null;
        try {
            String para = "";
            for (String key : param.keySet()) {
                para += (key + "=" + param.get(key) + "&");
            }
            if (para.lastIndexOf("&") > 0) {
                para = para.substring(0, para.length() - 1);
            }
            String urlNameString = url + "?" + para;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @param param  请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, String> param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            String para = "";
            for (String key : param.keySet()) {
                para += (key + "=" + param.get(key) + "&");
            }
            if (para.lastIndexOf("&") > 0) {
                para = para.substring(0, para.length() - 1);
            }
            String urlNameString = url + "?" + para;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}
