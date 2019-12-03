package com.example.test5.Model;

import java.io.Serializable;

public class Student implements Serializable {

    String hakbun, name, phone, imageName, hakgoa,  grade, classes, status, mento;

    public Student() {
    }

    public Student(String hakbun, String name, String phone, String imageName, String hakgoa, String grade, String classes, String status, String mento) {
        this.hakbun = hakbun;
        this.name = name;
        this.phone = phone;
        this.imageName = imageName;
        this.hakgoa = hakgoa;
        this.grade = grade;
        this.classes = classes;
        this.status = status;
        this.mento = mento;
    }

    public String getHakbun() {
        return hakbun;
    }

    public void setHakbun(String hakbun) {
        this.hakbun = hakbun;
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getHakgoa() {
        return hakgoa;
    }

    public void setHakgoa(String hakgoa) {
        this.hakgoa = hakgoa;
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
                "hakbun='" + hakbun + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", imageName='" + imageName + '\'' +
                ", hakgoa='" + hakgoa + '\'' +
                ", grade='" + grade + '\'' +
                ", classes='" + classes + '\'' +
                ", status='" + status + '\'' +
                ", mento='" + mento + '\'' +
                '}';
    }
}
