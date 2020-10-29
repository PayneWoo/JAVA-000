package util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * HttpClient 工具类
 * @author Payne
 * @date 2020/10/28
 */
public class HttpClientUtils {

    private final static Integer SOCKET_TIMEOUT = 20000;
    private final static Integer CONNECT_TIMEOUT = 5000;
    private final static Integer REQUEST_TIMEOUT = 5000;
    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom()
            .setSocketTimeout(SOCKET_TIMEOUT)
            .setConnectTimeout(CONNECT_TIMEOUT)
            .setConnectionRequestTimeout(REQUEST_TIMEOUT)
            .setRedirectsEnabled(true)
            .build();
    private static final HttpClient HTTP_CLIENT = HttpClients.custom()
            .setDefaultRequestConfig(REQUEST_CONFIG).build();


    /**
     * GET 请求
     * @param url 请求地址
     * @return 请求结果
     * @throws IOException I/O异常
     */
    public static String httpGet(String url) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(REQUEST_CONFIG);
            HttpResponse response = HTTP_CLIENT.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            inputStream = httpEntity.getContent();
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);

            String str;
            while ((str = bufferedReader.readLine()) != null) {
                stringBuilder.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert inputStream != null;
            inputStream.close();
            assert inputStreamReader != null;
            inputStreamReader.close();
            assert bufferedReader != null;
            bufferedReader.close();
        }

        return String.valueOf(stringBuilder);
    }


    /**
     * POST 请求
     * @param url 请求地址
     * @param contentType 内容类型，eg:application/json
     * @param postBody 请求体
     * @return 请求结果
     * @throws IOException I/O 异常
     */
    public static String httpPost(String url, String contentType, String postBody) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(REQUEST_CONFIG);
            httpPost.setHeader("Content-Type", contentType);
            httpPost.setHeader("Connection", "close");
            StringEntity entity = new StringEntity(postBody, StandardCharsets.UTF_8);
            httpPost.setEntity(entity);
            HttpResponse response = HTTP_CLIENT.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            inputStream = httpEntity.getContent();
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);

            String str;
            while ((str = bufferedReader.readLine()) != null) {
                stringBuilder.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert inputStream != null;
            inputStream.close();
            assert inputStreamReader != null;
            inputStreamReader.close();
            assert bufferedReader != null;
            bufferedReader.close();
        }

        return String.valueOf(stringBuilder);
    }
}
