package CorbaApp;


/**
* CorbaApp/DCMSHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Interface.idl
* Wednesday, 13 June, 2018 8:12:02 PM AKDT
*/

abstract public class DCMSHelper
{
  private static String  _id = "IDL:CorbaApp/DCMS:1.0";

  public static void insert (org.omg.CORBA.Any a, CorbaApp.DCMS that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static CorbaApp.DCMS extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (CorbaApp.DCMSHelper.id (), "DCMS");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static CorbaApp.DCMS read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_DCMSStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, CorbaApp.DCMS value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static CorbaApp.DCMS narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof CorbaApp.DCMS)
      return (CorbaApp.DCMS)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      CorbaApp._DCMSStub stub = new CorbaApp._DCMSStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static CorbaApp.DCMS unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof CorbaApp.DCMS)
      return (CorbaApp.DCMS)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      CorbaApp._DCMSStub stub = new CorbaApp._DCMSStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}