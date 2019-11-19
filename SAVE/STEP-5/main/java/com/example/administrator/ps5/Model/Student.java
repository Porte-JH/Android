package com.example.administrator.ps5.Model;

import java.io.Serializable;

public class Student implements Serializable {

    String num, name, phone, imagename, department;

    public Student(){

    }

    public Student(String num, String name, String phone, String imagename, String department) {
        this.num = num;
        this.name = name;
        this.phone = phone;
        this.imagename = imagename;
        this.department = department;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


}
