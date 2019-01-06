package com.hfc.spidernest.bean;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

/**
 * Created by user-hfc on 2019/1/6.
 */
public class HttpResponseEntity {

    private int statusCode;
    private HttpEntity entity;

    public HttpResponseEntity(int statusCode, HttpEntity entity) {
        this.statusCode = statusCode;
        this.entity = entity;
    }

    public HttpResponseEntity(HttpResponse response) {
        this.statusCode = response.getStatusLine().getStatusCode();
        this.entity = response.getEntity();
    }

    public static HttpResponseEntity getRespEntity(HttpResponse response) {
        if (null != response) {
            return new HttpResponseEntity(response);
        } else {
            return getRespEntity(0, null);
        }
    }

    public static HttpResponseEntity getRespEntity(int statusCode, HttpEntity entity) {
        return new HttpResponseEntity(statusCode, entity);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpEntity getEntity() {
        return entity;
    }
}
