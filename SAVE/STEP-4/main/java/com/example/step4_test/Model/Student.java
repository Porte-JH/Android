package com.example.step4_test.Model;

import java.io.Serializable;

public class Student implements Serializable {

    String num, name, phone, department, grade, classes, status, mento;

    public Student(){}

    public Student(String num, String name, String phone, String department, String grade, String classes, String status, String mento) {
        this.num = num;
        this.name = name;
        this.phone = phone;
        this.department = department;
        this.grade = grade;
        this.classes = classes;
        this.status = status;
        this.mento = mento;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMento() {
        return mento;
    }

    public void setMento(String mento) {
        this.mento = mento;
    }

    @Override
    public String toString() {
        return "Student{" +
                "num='" + num + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", department='" + department + '\'' +
                ", grade='" + grade + '\'' +
                ", classes='" + classes + '\'' +
                ", status='" + status + '\'' +
                ", mento='" + mento + '\'' +
                '}';
    }
}
