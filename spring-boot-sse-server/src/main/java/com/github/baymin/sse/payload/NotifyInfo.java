package com.github.baymin.sse.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通知信息载体
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotifyInfo {

    /**
     * 真实的用户ID
     */
    private String bizObjId;

    /**
     * 归属地市
     */
    private String location;

    /**
     * 厂站信息
     */
    private String unitName;

    /**
     * 消息业务类型
     */
    private String targetType;

    /**
     * 消息变更类型
     */
    private String changeType;

}
