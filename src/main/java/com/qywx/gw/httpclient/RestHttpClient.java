package com.qywx.gw.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class RestHttpClient extends HttpConstants {
    private static Logger logger = LoggerFactory.getLogger(HttpConstants.class);

    private static RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(HttpConstants.Config.timeOut)
            .setConnectTimeout(HttpConstants.Config.timeOut)
            .setSocketTimeout(HttpConstants.Config.timeOut)
            .build();


    public static String getHttpResponse(String url, String method, HashMap<String, String> headers, String contents) throws IOException {
        if (url == null || url.trim().equals("")) {
            return null;
        }
        if (Method.HTTP_METHOD_GET.equals(method)) {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            return execute(httpGet);

        } else if (Method.HTTP_METHOD_POST.equals(method)) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            if (contents != null) {
                logger.info(contents);
//                JsonObject returnData = new JsonParser().parse(contents).getAsJsonObject();
                StringEntity entity = new StringEntity(contents, "utf-8");//解决中文乱码问题
                entity.setContentType("application/json");

                httpPost.setEntity(entity);
                if (headers != null && headers.size() > 0) {
                    //设置请求头
                    Set<Map.Entry<String, String>> sets = headers.entrySet();
                    for (Map.Entry<String, String> entry : sets) {
                        httpPost.setHeader(entry.getKey(), entry.getValue());
                    }
                }
                logger.info("httpPost getEntity " + httpPost.getEntity().toString());
            }
            return execute(httpPost);
        } else {
            return null;
        }
    }

    public static String uploadFile(String url, String method, HashMap<String, String> headers, MultipartFile multipartFile) throws IOException {
        if (url == null || url.trim().equals("")) {
            return null;
        }
        if (Method.HTTP_METHOD_GET.equals(method)) {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            return execute(httpGet);

        } else if (Method.HTTP_METHOD_POST.equals(method)) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            if (multipartFile != null) {
                logger.info("upload wx " + multipartFile.getSize() + " " + multipartFile.getContentType() + " " + multipartFile.getOriginalFilename());

                ByteArrayBody bin = new ByteArrayBody(multipartFile.getBytes(), ContentType.IMAGE_JPEG, multipartFile.getOriginalFilename());
                StringBody comment = new StringBody("A binary file of some kind", ContentType.IMAGE_JPEG);

                HttpEntity reqEntity = MultipartEntityBuilder.create()
                        .addPart("bin", bin)
                        .addPart("comment", comment)
                        .build();
                httpPost.setEntity(reqEntity);
                if (headers != null && headers.size() > 0) {
                    //设置请求头
                    Set<Map.Entry<String, String>> sets = headers.entrySet();
                    for (Map.Entry<String, String> entry : sets) {
                        httpPost.setHeader(entry.getKey(), entry.getValue());
                    }
                }
                logger.info("httpPost getEntity " + httpPost.getEntity().toString());
            }
            return execute(httpPost);
        } else {
            return null;
        }
    }


    private static String execute(HttpUriRequest req) {

        if (req == null) {
            return null;
        }
        HttpConnectionManager httpConnectionManager = HttpConnectionManager.getInstance();
        CloseableHttpClient httpClient = httpConnectionManager.getHttpClient();
        CloseableHttpResponse resp = null;
        logger.info("httpConnectionManager : " + httpConnectionManager.cm.getTotalStats().toString());
        //发送https请求，获取响应码
        try {
            logger.info(" | req: " + req.toString());
            resp = httpClient.execute(req);
            if (resp == null) {
                logger.warn("execute  " + "|http response null");
                return null;
            } else if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                logger.warn("execute  " + "|http response code:"
                        + resp.getStatusLine().getStatusCode());
                return null;
            }

            logger.info(" | resp: " + resp.toString());
            HttpEntity entity = resp.getEntity();
            String respContent = EntityUtils.toString(entity, "UTF-8");
//            String respContent = EntityUtils.toString(entity, "GBK");
            InputStream in = resp.getEntity().getContent();
            in.close();
            logger.info(" | respContent: " + respContent);
            return respContent;
        } catch (ClientProtocolException e) {
            logger.warn("execute  " + "|exception: " + e.getMessage());
            return null;
        } catch (IOException e) {
            logger.warn("execute  " + "|exception: " + e.getMessage());
            return null;
        } finally {
            try {
                resp.close();
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }
}


