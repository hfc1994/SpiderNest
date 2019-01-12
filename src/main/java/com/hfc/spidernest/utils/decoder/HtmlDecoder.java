package com.hfc.spidernest.utils.decoder;

import java.util.List;

/**
 * Created by user-hfc on 2019/1/12.
 */
public interface HtmlDecoder<T> {

    List<T> decode(Object object);
}
