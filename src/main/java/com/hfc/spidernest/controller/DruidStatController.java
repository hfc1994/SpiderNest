package com.hfc.spidernest.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by user-hfc on 2019/1/6.
 */
@RestController
public class DruidStatController {

    @GetMapping("/druid/stat")
    public Object druidStat() {
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}
