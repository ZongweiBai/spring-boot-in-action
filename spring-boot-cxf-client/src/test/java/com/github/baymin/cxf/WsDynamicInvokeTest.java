package com.github.baymin.cxf;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.jupiter.api.Test;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * 使用JaxWsDynamicClientFactory调用
 * 只要指定服务器端wsdl文件的位置，然后指定要调用的方法和方法的参数即可，不关心服务端的实现方式。
 *
 * @author Zongwei
 * @date 2020/2/28 17:46
 */
public class WsDynamicInvokeTest {

    @Test
    public void proxyInvoke() throws Exception {
        /**
         * 以下两行代码是为了绕过证书验证调用webservice的https接口
         */
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(hv);

        JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
        // 使用mock server的地址进行访问
        Client client = clientFactory.createClient("https://192.168.3.168/soapmock/nameService?WSDL");
//        Client client = clientFactory.createClient("http://localhost:8088/nameService?WSDL");
        Object[] result = client.invoke("getName", "KEVIN");
        System.out.println(result[0]);
    }

    static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                .getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
                .getSocketFactory());
    }

    static HostnameVerifier hv = new HostnameVerifier() {
        public boolean verify(String urlHostName, SSLSession session) {
            System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
            return true;
        }
    };

    static class miTM implements javax.net.ssl.TrustManager,
            javax.net.ssl.X509TrustManager {
        @Override
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        @Override
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }
}
