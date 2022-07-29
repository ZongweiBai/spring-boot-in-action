package com.github.baymin.netty.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 传输的内容
 *
 * @author BaiZongwei
 * @date 2021/9/2 22:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferContent implements Serializable {

    private static final long serialVersionUID = -5458151674649898794L;

    private Long transactionId;

    private Object content;

}
