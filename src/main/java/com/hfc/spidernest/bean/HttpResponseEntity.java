package com.hfc.spidernest.bean;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

/**
 * Created by user-hfc on 2019/1/6.
 */
public class HttpResponseEntity {

    private int statusCode;
    private HttpResponse response;

    public HttpResponseEntity(int statusCode, HttpResponse response) {
        this.statusCode = statusCode;
        this.response = response;
    }

    public HttpResponseEntity(HttpResponse response) {
        this.statusCode = response.getStatusLine().getStatusCode();
        this.response = response;
    }

    public static HttpResponseEntity getRespEntity(HttpResponse response) {
        if (null != response) {
            return new HttpResponseEntity(response);
        } else {
            return getRespEntity(0, null);
        }
    }

    public static HttpResponseEntity getRespEntity(int statusCode, HttpResponse response) {
        return new HttpResponseEntity(statusCode, response);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpResponse getResponse() {
        return response;
    }
}
