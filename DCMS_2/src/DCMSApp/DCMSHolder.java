package DCMSApp;

/**
* DCMSApp/DCMSHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from dcms.idl
* Saturday, June 16, 2018 11:45:16 o'clock AM EDT
*/

public final class DCMSHolder implements org.omg.CORBA.portable.Streamable
{
  public DCMSApp.DCMS value = null;

  public DCMSHolder ()
  {
  }

  public DCMSHolder (DCMSApp.DCMS initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = DCMSApp.DCMSHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    DCMSApp.DCMSHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return DCMSApp.DCMSHelper.type ();
  }

}
