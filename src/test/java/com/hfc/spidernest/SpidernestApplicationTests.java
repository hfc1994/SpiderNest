package com.hfc.spidernest;

import com.hfc.spidernest.dao.mapper.TopicMapper;
import com.hfc.spidernest.entity.douban.Topic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpidernestApplicationTests {

    @Resource
    private TopicMapper topicMapper;

    @Test
    public void testTopicMapper() {
        Topic topic = topicMapper.selectByPrimaryKey(1);
        System.out.println(topic.getTitle());
        System.out.println(topic.getCreateTime());
        System.out.println(topic.getModifyTime());
    }

}

