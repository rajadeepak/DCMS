
package client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "LVLImplService", targetNamespace = "http://com/", wsdlLocation = "http://localhost:7777/lvl?wsdl")
public class LVLImplService
    extends Service
{

    private final static URL LVLIMPLSERVICE_WSDL_LOCATION;
    private final static WebServiceException LVLIMPLSERVICE_EXCEPTION;
    private final static QName LVLIMPLSERVICE_QNAME = new QName("http://com/", "LVLImplService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:7777/lvl?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        LVLIMPLSERVICE_WSDL_LOCATION = url;
        LVLIMPLSERVICE_EXCEPTION = e;
    }

    public LVLImplService() {
        super(__getWsdlLocation(), LVLIMPLSERVICE_QNAME);
    }

    public LVLImplService(WebServiceFeature... features) {
        super(__getWsdlLocation(), LVLIMPLSERVICE_QNAME, features);
    }

    public LVLImplService(URL wsdlLocation) {
        super(wsdlLocation, LVLIMPLSERVICE_QNAME);
    }

    public LVLImplService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, LVLIMPLSERVICE_QNAME, features);
    }

    public LVLImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public LVLImplService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns DCMSInterface
     */
    @WebEndpoint(name = "LVLImplPort")
    public DCMSInterface getLVLImplPort() {
        return super.getPort(new QName("http://com/", "LVLImplPort"), DCMSInterface.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns DCMSInterface
     */
    @WebEndpoint(name = "LVLImplPort")
    public DCMSInterface getLVLImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://com/", "LVLImplPort"), DCMSInterface.class, features);
    }

    private static URL __getWsdlLocation() {
        if (LVLIMPLSERVICE_EXCEPTION!= null) {
            throw LVLIMPLSERVICE_EXCEPTION;
        }
        return LVLIMPLSERVICE_WSDL_LOCATION;
    }

}
