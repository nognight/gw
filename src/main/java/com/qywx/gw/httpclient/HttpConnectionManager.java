package com.qywx.gw.httpclient;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

/**
 * Created by HiWin10 on 2017/4/7.
 * 连接池管理类，支持https协议
 */

public class HttpConnectionManager {
    private static Logger logger = LoggerFactory.getLogger(HttpConnectionManager.class);
    private HttpConnectionManager() {
        init();
    }

    private static HttpConnectionManager single = new HttpConnectionManager();

    public synchronized static HttpConnectionManager getInstance() {
        if (single == null) {
            single = new HttpConnectionManager();
        }
        return single;
    }

    public  PoolingHttpClientConnectionManager cm = null;


    public void init() {
        LayeredConnectionSocketFactory layeredConnectionSocketFactory = null;
        try {
            layeredConnectionSocketFactory = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", layeredConnectionSocketFactory)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(400);
        cm.setDefaultMaxPerRoute(200);
        logger.info("PoolingHttpClientConnectionManager init success " + cm.getTotalStats());
    }

    public CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();

        /*CloseableHttpClient httpClient = HttpClients.createDefault();//如果不采用连接池就是这种方式获取连接*/
        return httpClient;
    }
}