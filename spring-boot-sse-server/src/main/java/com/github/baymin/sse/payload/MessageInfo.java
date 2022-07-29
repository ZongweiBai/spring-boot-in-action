package com.github.baymin.sse.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 推送到前端的消息载体
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageInfo {

    /**
     * 消息业务类型
     */
    private String targetType;

    /**
     * 消息变更类型
     */
    private String changeType;

    public MessageInfo(String targetType, String changeType) {
        this.targetType = targetType;
        this.changeType = changeType;
    }

    public MessageInfo(NotifyInfo notifyInfo) {
        this.targetType = notifyInfo.getTargetType();
        this.changeType = notifyInfo.getChangeType();
    }
}
