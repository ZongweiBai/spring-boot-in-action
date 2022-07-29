package com.github.baymin.axis2;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;

/**
 * @author Zongwei
 * @date 2020/2/28 14:58
 */
public class RPCInvokeWsdlTest {

    @Test
    public void testInvoke() throws AxisFault {
        String address = "http://localhost:8889/services/nameService?wsdl";
        RPCServiceClient client = new RPCServiceClient();
        EndpointReference reference = new EndpointReference(address);
        Options options = client.getOptions();
        options.setTo(reference);
        QName qname = new QName("http://webservice.axis2.baymin.github.com", "getName");
        Object[] parameters = new Object[]{"chen"};
        Class[] returnTypes = new Class[]{String.class};
        Object[] response = client.invokeBlocking(qname, parameters, returnTypes);
        System.out.println(response[0]);
    }

    /**
     *
     */
    @Test
    public void testDocument() {
        try {
            // String url = "http://localhost:8080/axis2ServerDemo/services/StockQuoteService";
            String url = "http://localhost:8889/services/nameService?wsdl";

            Options options = new Options();
            EndpointReference targetEPR = new EndpointReference(url);
            options.setTo(targetEPR);
            // options.setAction("urn:getPrice");

            ServiceClient sender = new ServiceClient();
            sender.setOptions(options);


            OMFactory fac = OMAbstractFactory.getOMFactory();
            String tns = "http://webservice.axis2.baymin.github.com";
            OMNamespace omNs = fac.createOMNamespace(tns, "");
            OMElement method = fac.createOMElement("getName", omNs);
            OMElement symbol = fac.createOMElement("name", omNs);
            symbol.setText("wangwu");
            //symbol.addChild(fac.createOMText(symbol, "Axis2 Echo String "));
            method.addChild(symbol);
            method.build();
            OMElement result = sender.sendReceive(method);

            System.out.println(result);

        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
    }

}
