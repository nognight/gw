package com.qywx.gw.controller;


import com.qywx.gw.httpclient.HttpConstants;
import com.qywx.gw.httpclient.RestHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping(value = "/*")
    private String testApi(HttpSession httpSession , @RequestHeader Map<String,String> header) {
        logger.info("测试api");
        logger.info(header.toString());
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        String resp="";
        try {
             resp = reqBeanService(url.toString(), HttpConstants.Method.HTTP_METHOD_GET,null);
            logger.info(resp);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return resp;
    }




    /**
     * 请求方法
     *
     * @param url
     * @param method
     * @param reqContents
     * @return
     * @throws IOException
     */
    private String reqBeanService(String url, String method, String reqContents) throws IOException {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-type", "application/json; charset=UTF-8");
        headers.put("Accept", "application/json");
        headers.put("Accept-Encoding", "");
        String response = RestHttpClient.getHttpResponse(url, method, headers, reqContents);
        if (response == null) {
            logger.error("Exception(Read Object): Rest Response error");
            return null;
        }
        return response;

    }
}
