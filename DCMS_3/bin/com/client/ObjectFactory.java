
package com.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.client package. 
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

    private final static QName _CreateSRecord_QNAME = new QName("http://com/", "createSRecord");
    private final static QName _CreateSRecordResponse_QNAME = new QName("http://com/", "createSRecordResponse");
    private final static QName _CreateTRecordResponse_QNAME = new QName("http://com/", "createTRecordResponse");
    private final static QName _CreateTRecord_QNAME = new QName("http://com/", "createTRecord");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateSRecordResponse }
     * 
     */
    public CreateSRecordResponse createCreateSRecordResponse() {
        return new CreateSRecordResponse();
    }

    /**
     * Create an instance of {@link CreateSRecord }
     * 
     */
    public CreateSRecord createCreateSRecord() {
        return new CreateSRecord();
    }

    /**
     * Create an instance of {@link CreateTRecord }
     * 
     */
    public CreateTRecord createCreateTRecord() {
        return new CreateTRecord();
    }

    /**
     * Create an instance of {@link CreateTRecordResponse }
     * 
     */
    public CreateTRecordResponse createCreateTRecordResponse() {
        return new CreateTRecordResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com/", name = "createSRecord")
    public JAXBElement<CreateSRecord> createCreateSRecord(CreateSRecord value) {
        return new JAXBElement<CreateSRecord>(_CreateSRecord_QNAME, CreateSRecord.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSRecordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com/", name = "createSRecordResponse")
    public JAXBElement<CreateSRecordResponse> createCreateSRecordResponse(CreateSRecordResponse value) {
        return new JAXBElement<CreateSRecordResponse>(_CreateSRecordResponse_QNAME, CreateSRecordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTRecordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com/", name = "createTRecordResponse")
    public JAXBElement<CreateTRecordResponse> createCreateTRecordResponse(CreateTRecordResponse value) {
        return new JAXBElement<CreateTRecordResponse>(_CreateTRecordResponse_QNAME, CreateTRecordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com/", name = "createTRecord")
    public JAXBElement<CreateTRecord> createCreateTRecord(CreateTRecord value) {
        return new JAXBElement<CreateTRecord>(_CreateTRecord_QNAME, CreateTRecord.class, null, value);
    }

}
