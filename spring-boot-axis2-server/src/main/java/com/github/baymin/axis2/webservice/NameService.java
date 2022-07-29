package com.github.baymin.axis2.webservice;

import org.springframework.stereotype.Component;

/**
 * Webservice接口方法
 *
 * @author yulj
 * @create: 2019/03/12 19:50
 */
@Component
public class NameService {

    public String getName(String name) {
        System.out.println("your name is " + name);
        return "your name is " + name;
    }

}