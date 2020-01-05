package com.example.entreprisemanager;

public class ModelEmployee {

    private String employee_name , employee_email ,employee_phonenumber ;


    public ModelEmployee (String employee_name ,String employee_email ,String employee_phonenumber ){
        this.employee_name = employee_name ;
        this.employee_email = employee_email ;
        this.employee_phonenumber = employee_phonenumber ;

    }
    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_email() {
        return employee_email;
    }

    public void setEmployee_email(String employee_email) {
        this.employee_email = employee_email;
    }

    public String getEmployee_phonenumber() {
        return employee_phonenumber;
    }

    public void setEmployee_phonenumber(String employee_phonenumber) {
        this.employee_phonenumber = employee_phonenumber;
    }
}
