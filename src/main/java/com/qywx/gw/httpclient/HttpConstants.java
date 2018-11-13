package com.qywx.gw.httpclient;

/**
 *
 */
public class HttpConstants {
    /**
     * <p>Description: HTTP请求方法</p>
     */

    public class Method {
        /**
         * HTTP METHOD GET.
         */
        public static final String HTTP_METHOD_GET = "GET";
        /**
         * HTTP METHOD PUT.
         */
        public static final String HTTP_METHOD_PUT = "PUT";
        /**
         * HTTP METHOD DELETE.
         */
        public static final String HTTP_METHOD_DELETE = "DELETE";
        /**
         * HTTP METHOD POST.
         */
        public static final String HTTP_METHOD_POST = "POST";
    }

    /**
     * <p>Description: REST响应码</p>
     */
    public class Status {
        /**
         * HTTP STATUS 200.
         */
        public static final int HTTP_STATUS_200 = 200;
        /**
         * HTTP STATUS 201.
         */
        public static final int HTTP_STATUS_201 = 201;
        /**
         * HTTP STATUS 202.
         */
        public static final int HTTP_STATUS_202 = 202;
        /**
         * HTTP STATUS 204.
         */
        public static final int HTTP_STATUS_204 = 204;
        /**
         * HTTP STATUS 206.
         */
        public static final int HTTP_STATUS_206 = 206;
        /**
         * HTTP STATUS 400.
         */
        public static final int HTTP_STATUS_400 = 400;
        /**
         * HTTP STATUS 416.
         */
        public static final int HTTP_STATUS_416 = 416;
    }

    /**
     * <p>Description: 请求 返回码 </p>
     */
    public class Code {
        /**
         * CODE 0000.
         */
        public static final String RESPONSE_CODE_0000 = "0000";
        /**
         * CODE 99999.
         */
        public static final String RESPONSE_CODE_ERROR = "99999";
        /**
         * CODE 99999 desc.
         */
        public static final String RESPONSE_CODE_ERRORDESC = "System Error";
        /**
         * CODE 99000.
         */
        public static final String RESPONSE_CODE_9999 = "99000";
        /**
         * CODE 0.
         */
        public static final String RESPONSE_CODE_0 = "0";
        /**
         * CODE 1548.
         */
        public static final String RESPONSE_CODE_1548 = "1548";
        /**
         * CODE 1549.
         */
        public static final String RESPONSE_CODE_1549 = "1549";


    }

    /**
     * <p>Description: bool值 </p>
     */
    public class Result {
        /**
         * RESULT 1.
         */
        public static final String RESULT_CODE_1 = "1";
        /**
         * RESULT 2.
         */
        public static final String RESULT_CODE_2 = "2";
    }


    public class Config {
        public static final int timeOut = 10 * 1000;
    }


}
