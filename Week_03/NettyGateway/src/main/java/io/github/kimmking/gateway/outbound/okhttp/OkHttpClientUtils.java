package io.github.kimmking.gateway.outbound.okhttp;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * OkHttp 工具类
 * @author Payne
 * @date 2020/10/28
 */
public class OkHttpClientUtils {

    private final static long CONNECT_TIMEOUT = 5;
    private final static long WRITE_TIMEOUT = 10;
    private final static long READ_TIMEOUT = 10;

    private final static String USER_AGENT = "Mozilla/5.0 (MacOS;)";


    /**
     * GET 请求
     * @param url 请求地址
     * @return 请求结果
     */
    public static String doGet(String url) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url).get().header("User-Agent", USER_AGENT)
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                return null;
            }
            return responseBody.string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * POST 请求
     * @param url 请求地址
     * @param mediaType 数据类型,eg: MediaType.parse("application/json; charset=UTF-8")
     * @param content 请求体内容
     * @return 请求结果
     */
    public static String doPost(String url, MediaType mediaType, String content) {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                return null;
            }
            return responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
