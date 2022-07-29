package com.github.baymin.axis2;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;

/**
 * @author Zongwei
 * @date 2020/2/28 15:17
 */
public class RPCInvokeSoapTest {

    @Test
    public void client2() {
        String address = "http://localhost:8889/services/nameService";
        try {
            RPCServiceClient client = new RPCServiceClient();
            EndpointReference reference = new EndpointReference(address);
            Options options = client.getOptions();
            options.setTo(reference);
            QName qname = new QName("http://webservice.axis2.baymin.github.com", "getName");
            Object[] parameters = new Object[]{"aliax"};
            Class[] returnTypes = new Class[]{String.class};
            //client.invokeRobust(qname, parameters);
            Object[] objects = client.invokeBlocking(qname, parameters, returnTypes);
            client.cleanupTransport();
            System.out.println(objects[0]);
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
    }

}
