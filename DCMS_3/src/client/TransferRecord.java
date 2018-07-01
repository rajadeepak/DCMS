
package client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for transferRecord complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="transferRecord">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ManagerID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RecordID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewServerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transferRecord", propOrder = {
    "managerID",
    "recordID",
    "newServerName"
})
public class TransferRecord {

    @XmlElement(name = "ManagerID")
    protected String managerID;
    @XmlElement(name = "RecordID")
    protected String recordID;
    @XmlElement(name = "NewServerName")
    protected String newServerName;

    /**
     * Gets the value of the managerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManagerID() {
        return managerID;
    }

    /**
     * Sets the value of the managerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManagerID(String value) {
        this.managerID = value;
    }

    /**
     * Gets the value of the recordID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordID() {
        return recordID;
    }

    /**
     * Sets the value of the recordID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordID(String value) {
        this.recordID = value;
    }

    /**
     * Gets the value of the newServerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewServerName() {
        return newServerName;
    }

    /**
     * Sets the value of the newServerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewServerName(String value) {
        this.newServerName = value;
    }

}
