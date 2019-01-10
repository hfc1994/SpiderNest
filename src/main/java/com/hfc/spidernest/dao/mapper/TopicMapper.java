package com.hfc.spidernest.dao.mapper;

import com.hfc.spidernest.bean.douban.Topic;
import java.util.List;

public interface TopicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Topic record);

    Topic selectByPrimaryKey(Integer id);

    List<Topic> selectAll();

    int updateByPrimaryKey(Topic record);
}