package com.github.baymin.sse.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户基本信息
 */
@Data
@NoArgsConstructor
public class UserBaseInfo {

    /**
     * 建立sse连接的用户ID
     */
    private String userId;

    /**
     * 真实的用户ID
     */
    private String bizObjId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 归属地市
     */
    private String location;

    /**
     * 厂站信息
     */
    private String unitName;

}
