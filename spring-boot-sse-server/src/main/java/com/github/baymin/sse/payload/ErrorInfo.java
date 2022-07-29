package com.github.baymin.sse.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zongwei
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo {

    private String error;

    private String errorDescription;

}
