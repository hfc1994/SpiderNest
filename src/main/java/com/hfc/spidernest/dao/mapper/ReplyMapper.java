package com.hfc.spidernest.dao.mapper;

import com.hfc.spidernest.bean.douban.Reply;
import java.util.List;

public interface ReplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Reply record);

    Reply selectByPrimaryKey(Integer id);

    List<Reply> selectAll();

    int updateByPrimaryKey(Reply record);
}