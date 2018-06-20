package CorbaApp;


/**
* CorbaApp/DCMSPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Interface.idl
* Wednesday, 13 June, 2018 8:12:02 PM AKDT
*/

public abstract class DCMSPOA extends org.omg.PortableServer.Servant
 implements CorbaApp.DCMSOperations, org.omg.CORBA.portable.InvokeHandler
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
       case 0:  // CorbaApp/DCMS/createTRecord
       {
         String ManagerID = in.read_string ();
         String firstName = in.read_string ();
         String lastName = in.read_string ();
         String address = in.read_string ();
         String phone = in.read_string ();
         String specialization = in.read_string ();
         String location = in.read_string ();
         String $result = null;
         $result = this.createTRecord (ManagerID, firstName, lastName, address, phone, specialization, location);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // CorbaApp/DCMS/createSRecord
       {
         String ManagerID = in.read_string ();
         String firstName = in.read_string ();
         String lastName = in.read_string ();
         String courseRegistered = in.read_string ();
         String status = in.read_string ();
         String statusDate = in.read_string ();
         String $result = null;
         $result = this.createSRecord (ManagerID, firstName, lastName, courseRegistered, status, statusDate);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 2:  // CorbaApp/DCMS/getRecordCounts
       {
         String ManagerID = in.read_string ();
         String $result = null;
         $result = this.getRecordCounts (ManagerID);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 3:  // CorbaApp/DCMS/editRecord
       {
         String ManagerID = in.read_string ();
         String recordID = in.read_string ();
         String fieldName = in.read_string ();
         String newValue = in.read_string ();
         String $result = null;
         $result = this.editRecord (ManagerID, recordID, fieldName, newValue);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 4:  // CorbaApp/DCMS/transferRecord
       {
         String managerID = in.read_string ();
         String recordID = in.read_string ();
         String remoteCenterServerName = in.read_string ();
         String $result = null;
         $result = this.transferRecord (managerID, recordID, remoteCenterServerName);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CorbaApp/DCMS:1.0"};

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
