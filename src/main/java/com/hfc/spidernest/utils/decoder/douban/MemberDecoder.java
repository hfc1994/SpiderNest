package com.hfc.spidernest.utils.decoder.douban;

import com.hfc.spidernest.entity.douban.Member;
import com.hfc.spidernest.utils.decoder.HtmlDecoder;
import com.hfc.spidernest.utils.exception.NotSuitableClassException;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user-hfc on 2019/1/21.
 */
public class MemberDecoder implements HtmlDecoder<Member> {

    /**
     * 解析成员页面
     * @param object 成员页面节点
     * @return 单个成员对象
     * @throws NotSuitableClassException 传入的类型不合适，需要的是Document
     */
    @Override
    public List<Member> decodeAllNode(Object object) throws NotSuitableClassException {
        if (object instanceof Document) {
            List<Member> members = new ArrayList<>(1);
            members.add(decodeNode(object));
            return members;
        } else {
            throw new NotSuitableClassException(Document.class, object);
        }

    }

    /**
     * 解析成员页面
     * @param object 成员页面节点
     * @return 单个成员对象
     * @throws NotSuitableClassException 传入的类型不合适，需要的是Document
     */
    @Override
    public Member decodeNode(Object object) throws NotSuitableClassException  {
        if (object instanceof  Document) {
            Document doc = (Document) object;

        } else {
            throw new NotSuitableClassException(Document.class, object);
        }
        return null;
    }
}
