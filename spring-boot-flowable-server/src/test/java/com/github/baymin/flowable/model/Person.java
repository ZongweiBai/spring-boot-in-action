package com.github.baymin.flowable.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author BaiZongwei
 * @date 2021/8/10 10:21
 */
@Data
public class Person implements Serializable {
    private static final long serialVersionUID = 8379071759772449529L;
    private Integer id;
    private String name;
    private String education;
}
