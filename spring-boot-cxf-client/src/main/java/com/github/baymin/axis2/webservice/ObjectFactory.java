
package com.github.baymin.axis2.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.github.baymin.axis2.webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetNameName_QNAME = new QName("http://webservice.axis2.baymin.github.com", "name");
    private final static QName _GetNameResponseReturn_QNAME = new QName("http://webservice.axis2.baymin.github.com", "return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.github.baymin.axis2.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetName }
     * 
     */
    public GetName createGetName() {
        return new GetName();
    }

    /**
     * Create an instance of {@link GetNameResponse }
     * 
     */
    public GetNameResponse createGetNameResponse() {
        return new GetNameResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.axis2.baymin.github.com", name = "name", scope = GetName.class)
    public JAXBElement<String> createGetNameName(String value) {
        return new JAXBElement<String>(_GetNameName_QNAME, String.class, GetName.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.axis2.baymin.github.com", name = "return", scope = GetNameResponse.class)
    public JAXBElement<String> createGetNameResponseReturn(String value) {
        return new JAXBElement<String>(_GetNameResponseReturn_QNAME, String.class, GetNameResponse.class, value);
    }

}
