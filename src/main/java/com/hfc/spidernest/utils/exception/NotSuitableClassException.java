package com.hfc.spidernest.utils.exception;

/**
 * Created by user-hfc on 2019/1/13.
 */
public class NotSuitableClassException extends RuntimeException {

    public NotSuitableClassException() {
        super();
    }

    public NotSuitableClassException(Class clazz, Object src) {
        super("except " + clazz.getName() + ",but found " + src.getClass().getName());
    }
}
