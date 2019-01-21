package com.hfc.spidernest.dao.mapper;

import com.hfc.spidernest.entity.douban.Member;

import java.util.List;

public interface MemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    Member selectByPrimaryKey(Integer id);

    List<Member> selectAll();

    int updateByPrimaryKey(Member record);
}