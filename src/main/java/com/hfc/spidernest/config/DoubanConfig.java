package com.hfc.spidernest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by user-hfc on 2019/1/6.
 */
@Configuration
public class DoubanConfig {

    @Value("${douban.startUrl}")
    private String startUrl;
}
