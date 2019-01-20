package com.hfc.spidernest.utils.decoder.douban;

import com.hfc.spidernest.entity.douban.Reply;
import com.hfc.spidernest.utils.decoder.HtmlDecoder;
import com.hfc.spidernest.utils.exception.NotSuitableClassException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by user-hfc on 2019/1/14.
 */
public class ReplyDecoder implements HtmlDecoder<Reply> {

    @Override
    public List<Reply> decode(Object object) throws NotSuitableClassException{
        if (object instanceof Document) {
            List<Reply> replyList = new ArrayList<>(8);
            Document doc = (Document) object;

            // https://www.douban.com/group/topic/131344244/?start=0
            Element commentBody = doc.getElementById("comments");
            Elements comments = commentBody.children();
            for (Element comment : comments) {
                Element replyDoc = comment.select(".reply-doc").first();
                Element head4 = replyDoc.children().first().children().first();
                Element replier = head4.children().first();
                String name = replier.text();
                String url = replier.attr("href");
                String content = replyDoc.children().select("p").first().text();

                System.out.println("----");
                System.out.println(name + "（" + url + "）回复说“" + content  + "”");
            }


            return replyList;
        } else {
            throw new NotSuitableClassException(Document.class, object);
        }
    }
}
