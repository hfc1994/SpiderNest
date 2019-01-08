package com.hfc.spidernest;

import com.hfc.spidernest.utils.httpclients.DoubanClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by user-hfc on 2019/1/8.
 */
public class NormalTest {

    public static void main(String... args) {
//        Random random = new Random();
//
//        for (int i=0; i<100; i++) {
//            if (random.nextInt(20) == 20) {
//                System.out.println("---true");
//            } else {
//                System.out.println("false");
//            }
//        }

//        List<String> headers = new ArrayList<>(6);
//        headers.add("aaa");
//        headers.add("bbb");
//        headers.add("ccc");
//        headers.add("ddd");
//        headers.add("eee");
//        headers.add("fff");
//
//        Random random = new Random();
//        for (int i=0; i<50; i++) {
//            printList(headers);
//            headers.set(5, "ooo" + random.nextInt(10));
//        }

        DoubanClient db = new DoubanClient();
        for (int i=0; i<10; i++) {
            System.out.println(db.buildHeaders().get(5).getValue());
        }
    }

    public static void printList(List<?> objs) {
        objs.forEach(obj -> {
            System.out.print(obj);
            System.out.print(" ");
        });
        System.out.println();
    }
}
