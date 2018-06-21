package DCMSApp;


/**
* DCMSApp/DCMSPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from dcms.idl
* Saturday, June 16, 2018 11:45:16 o'clock AM EDT
*/

public abstract class DCMSPOA extends org.omg.PortableServer.Servant
 implements DCMSApp.DCMSOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("createTRecord", new java.lang.Integer (0));
    _methods.put ("createSRecord", new java.lang.Integer (1));
    _methods.put ("getRecordCounts", new java.lang.Integer (2));
    _methods.put ("editRecord", new java.lang.Integer (3));
    _methods.put ("transferRecord", new java.lang.Integer (4));
    _methods.put ("shutdown", new java.lang.Integer (5));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // DCMSApp/DCMS/createTRecord
       {
         String firstName = in.read_string ();
         String lastName = in.read_string ();
         String address = in.read_string ();
         String phone = in.read_string ();
         String specialization = in.read_string ();
         String location = in.read_string ();
         boolean $result = false;
         $result = this.createTRecord (firstName, lastName, address, phone, specialization, location);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 1:  // DCMSApp/DCMS/createSRecord
       {
         String firstName = in.read_string ();
         String lastName = in.read_string ();
         String courseRegistered = in.read_string ();
         String status = in.read_string ();
         int statusDate = in.read_long ();
         boolean $result = false;
         $result = this.createSRecord (firstName, lastName, courseRegistered, status, statusDate);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 2:  // DCMSApp/DCMS/getRecordCounts
       {
         String $result[] = null;
         $result = this.getRecordCounts ();
         out = $rh.createReply();
         DCMSApp.stringarrayHelper.write (out, $result);
         break;
       }

       case 3:  // DCMSApp/DCMS/editRecord
       {
         String recordID = in.read_string ();
         String fieldName = in.read_string ();
         String newValue = in.read_string ();
         boolean $result = false;
         $result = this.editRecord (recordID, fieldName, newValue);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 4:  // DCMSApp/DCMS/transferRecord
       {
         String managerId = in.read_string ();
         String recordId = in.read_string ();
         String remoteCenterLocation = in.read_string ();
         boolean $result = false;
         $result = this.transferRecord (managerId, recordId, remoteCenterLocation);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 5:  // DCMSApp/DCMS/shutdown
       {
         this.shutdown ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:DCMSApp/DCMS:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public DCMS _this() 
  {
    return DCMSHelper.narrow(
    super._this_object());
  }

  public DCMS _this(org.omg.CORBA.ORB orb) 
  {
    return DCMSHelper.narrow(
    super._this_object(orb));
  }


} // class DCMSPOA