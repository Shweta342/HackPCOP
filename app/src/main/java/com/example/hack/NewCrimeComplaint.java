package com.example.hack;

public class NewCrimeComplaint {

    private String Name, Age, Email_ID, Pincode, Address, Type_Of_Crime, Problem, Workplace, Other_Problem;

    public String getName() {
        return Name;
    }

    public String getAge() {
        return Age;
    }

    public String getEmailId() {
        return Email_ID;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }

    public void setEmailId(String Email_ID) {
        this.Email_ID = Email_ID;
    }

    public void setPin(String pin) {
        this.Pincode = pin;
    }

    public void setAdd(String Address) {
        this.Address = Address;
    }

    public void setSub_category(String Type_Of_Crime) {
        this.Type_Of_Crime = Type_Of_Crime;
    }

    public void setProb(String Problem) {
        this.Problem = Problem;
    }

    public void setComp(String Workplace) {
        this.Workplace = Workplace;
    }

    public void setOther(String Other_Problem) {
        this.Other_Problem = Other_Problem;
    }

    public String getPin() {
        return Pincode;
    }

    public String getAdd() {
        return Address;
    }

    public String getSub_category() {
        return Type_Of_Crime;
    }

    public String getProb() {
        return Problem;
    }

    public String getComp() {
        return Workplace;
    }

    public String getOther() {
        return Other_Problem;
    }
}

