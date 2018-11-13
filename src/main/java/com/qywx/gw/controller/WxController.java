package com.qywx.gw.controller;


import com.qywx.gw.httpclient.HttpConstants;
import com.qywx.gw.httpclient.RestHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WxController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(value = "/post")
    private String postToWx(HttpServletRequest httpServletRequest, @RequestHeader Map<String, String> reqHeader, @RequestBody String str) {
        String ip = getIpAddr(httpServletRequest);
        String url = reqHeader.get("qy-wx-req-url");
        logger.info("ip " + ip + " Post qy-wx-req-url: " + url);

        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-type", "application/json; charset=UTF-8");
            headers.put("Accept", "application/json");
            headers.put("Accept-Encoding", "");
            String response = RestHttpClient.getHttpResponse(url, HttpConstants.Method.HTTP_METHOD_POST, headers, str);
            if (response == null) {
                logger.error("Exception(Read Object): Rest Response error");
                return null;
            }
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @PostMapping(value = "/upload")
    private String uploadToWx(HttpServletRequest httpServletRequest, @RequestHeader Map<String, String> reqHeader, @RequestParam("file") MultipartFile multipartFile) {
        String ip = getIpAddr(httpServletRequest);
        String url = reqHeader.get("qy-wx-req-url");
        logger.info("ip " + ip + " Get qy-wx-req-url: " + url + "multipartFile " + multipartFile.getOriginalFilename() + " " + multipartFile.getContentType() + " " + multipartFile.getSize());

        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-type", "multipart/form-data");
            headers.put("Accept", "application/json");
            headers.put("Accept-Encoding", "");
            String response = RestHttpClient.uploadFile(url, HttpConstants.Method.HTTP_METHOD_POST, headers, multipartFile);
            if (response == null) {
                logger.error("Exception(Read Object): Rest Response error");
                return null;
            }
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    @GetMapping(value = "/get")
    private String getToWx(HttpServletRequest httpServletRequest, @RequestHeader Map<String, String> reqHeader) {
        String ip = getIpAddr(httpServletRequest);
        String url = reqHeader.get("qy-wx-req-url");
        logger.info("ip " + ip + " Get qy-wx-req-url: " + url);

        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-type", "application/json; charset=UTF-8");
            headers.put("Accept", "application/json");
            headers.put("Accept-Encoding", "");
            String response = RestHttpClient.getHttpResponse(url, HttpConstants.Method.HTTP_METHOD_GET, headers, null);
            if (response == null) {
                logger.error("Exception(Read Object): Rest Response error");
                return null;
            }
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * getIpAddr
     *
     * @param request
     * @return
     */
    private String getIpAddr(HttpServletRequest request) {

        String ip = "";
        //   String ip = request.getHeader("x-source-id");
        // String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
