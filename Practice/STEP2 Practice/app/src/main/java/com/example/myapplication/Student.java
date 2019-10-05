package com.example.myapplication;

import java.io.Serializable;
import com.example.myapplication.*;

public class Student implements Serializable {

    String num, name, phone;

    public Student(){
    }


    public Student(String num, String name, String phone) {
        this.num = num;
        this.name = name;
        this.phone = phone;
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

    @Override
    public String toString() {
        return "Student{" +
                "num='" + num + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

// Serializable implements >> 각 변수 선언 >> getter and setter, constructor, tostring 생성.
