package com.github.baymin.springnative.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 自定义消息体
 *
 * @author BaiZongwei
 * @date 2021/9/1 22:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferMessage {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private byte type;

    private int length;

    private TransferContent content;

    public TransferMessage(TransferContent content) throws IOException {
//        content.get
//        try {
//            this.content = OBJECT_MAPPER.writeValueAsString(content);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        this.content = content;

        ByteArrayOutputStream byam = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byam);
        oos.writeObject(content);
        byte[] bytes = byam.toByteArray();
        this.type = (byte) 0;
        this.length = bytes.length;
//        this.length = this.content.getBytes(StandardCharsets.UTF_8).length;
    }
}
