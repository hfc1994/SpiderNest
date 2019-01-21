package com.hfc.spidernest.utils.decoder.douban;

import com.hfc.spidernest.entity.douban.Member;
import com.hfc.spidernest.utils.decoder.HtmlDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user-hfc on 2019/1/21.
 */
public class MemberDecoder implements HtmlDecoder<Member> {

    @Override
    public List<Member> decodeAllNode(Object object) {
        List<Member> members = new ArrayList<>(1);
        members.add(decodeNode(object));
        return members;
    }

    @Override
    public Member decodeNode(Object object) {
        return null;
    }
}
