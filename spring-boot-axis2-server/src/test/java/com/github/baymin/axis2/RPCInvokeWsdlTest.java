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
import org.junit.Test;

import javax.xml.namespace.QName;

/**
 * 使用RPC方式调用服务端接口,远程wsdl
 *
 * @author Zongwei
 * @date 2020/2/28 14:58
 */
public class RPCInvokeWsdlTest {

    @Test
    public void testInvoke() throws AxisFault {
        String address = "http://localhost:8889/services/nameService?wsdl";
        RPCServiceClient client = new RPCServiceClient();
        EndpointReference reference = new EndpointReference(address);
        // 调用服务端接口，需要注意的是，注意创建Options对象，需要通过RPCServiceClient创建，不要new，否则就不会关联，这是容易出错的。
        Options options = client.getOptions();
        options.setTo(reference);
        // 命名空间，默认命名空间是当前类所属的包名倒过来，比如:服务类为com.fsats.axis.NameService,则明命名空间默认为http://axis.fsats.com
        QName qname = new QName("http://webservice.axis2.baymin.github.com", "getName");
        //指定参数
        Object[] parameters = new Object[]{"chen"};
        //指定返回类型
        Class[] returnTypes = new Class[]{String.class};
        Object[] response = client.invokeBlocking(qname, parameters, returnTypes);
        System.out.println(response[0]);
    }

    /**
     * 方法二： 应用document方式调用(构建请求报文)
     * 用ducument方式应用现对繁琐而灵活。现在用的比较多。因为真正摆脱了我们不想要的耦合
     */
    @Test
    public void testDocument() {
        try {
            // String url = "http://localhost:8080/axis2ServerDemo/services/StockQuoteService";
            String url = "http://localhost:8889/services/nameService?wsdl";

            Options options = new Options();
            // 指定调用WebService的URL
            EndpointReference targetEPR = new EndpointReference(url);
            options.setTo(targetEPR);
            // options.setAction("urn:getPrice");

            ServiceClient sender = new ServiceClient();
            sender.setOptions(options);


            OMFactory fac = OMAbstractFactory.getOMFactory();
            String tns = "http://webservice.axis2.baymin.github.com";
            // 命名空间，有时命名空间不增加没事，不过最好加上，因为有时有事，你懂的
            // 第一个参数命名空间地址，第二个参数是命名空间前缀
            OMNamespace omNs = fac.createOMNamespace(tns, "");
            // 第一个参数是构建标签名称，第二个参数是命名空间
            OMElement method = fac.createOMElement("getName", omNs);
            OMElement symbol = fac.createOMElement("name", omNs);
            // 标签设值
            symbol.setText("wangwu");
            //symbol.addChild(fac.createOMText(symbol, "Axis2 Echo String "));
            // 添加子标签
            method.addChild(symbol);
            // 构建报文
            method.build();
            // 发送请求返回报文
            OMElement result = sender.sendReceive(method);

            System.out.println(result);

        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
    }

}
