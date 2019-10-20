package com.example.administrator.myapplication;

import java.io.Serializable;

public class Student implements Serializable {

    String num, name, phone;

    public Student(){

    }

    public Student(String num, String name, String phone){
        this.num = num;
        this.name = name;
        this.phone = phone;
    }

    // getter
    public String getNum(){
        return num;
    }
    public String getName(){
        return name;
    }
    public String getPhone(){
        return phone;
    }

    //setter
    public void setNum(String num) {
        this.num = num;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override //변수 정보를 알기 위함?
    public String toString() {
        return "Student{" +
                "num='" + num + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}
