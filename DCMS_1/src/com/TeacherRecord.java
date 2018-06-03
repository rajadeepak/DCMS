package com;

public class TeacherRecord extends Record {
	String Address;
	String  Phone;
    String Specialization;
	String Location;
	String Record_ID;
    TeacherRecord(){}
	TeacherRecord(String firstname, String lastname,String address, String phone,String specialization,String location, String redordID) {
		super(firstname, lastname);
		// TODO Auto-generated constructor stub
		
		Address=address;
		Phone=phone;
		Specialization=specialization;
		Location=location;
	}
     
	public void updateRecordID(String id)
    {
		Record_ID = id;
    }

    public String getRecordID(){
        return Record_ID;
    }

    public void updateAddress(String add) {
        this.Address = add;
    }

    public String getAddress() {
        return Address;
    }

    public void updatePhone(String ph) {
        this.Phone = ph;
    }

    public String getPhone() {
        return Phone;
    }

    public void updateSpecialization(String spec) {
        this.Specialization = spec;
    }

    public String getSpecialization() {
        return Specialization;
    }
    
    public void updateLocation(String loc) {
        this.Location = loc;
    }
    
    public String getLocation() {
        return Location;
    }
}
