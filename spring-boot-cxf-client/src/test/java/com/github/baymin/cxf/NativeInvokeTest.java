package com.github.baymin.cxf;

import com.github.baymin.axis2.webservice.NameService;
import com.github.baymin.axis2.webservice.NameServicePortType;
import org.junit.jupiter.api.Test;

/**
 * 原生代码调用
 *
 * @author Zongwei
 * @date 2020/2/28 16:51
 */
public class NativeInvokeTest {

    @Test
    public void nativeInvoke() {
        NameService nameService = new NameService(NameService.WSDL_LOCATION, NameService.SERVICE);
        NameServicePortType nameServicePortType = nameService.getNameServiceHttpEndpoint();
        String response = nameServicePortType.getName("lisi");
        System.out.println(response);
    }

}
