package com.example.myapplication;

import java.io.Serializable;

//Serializable 객체를 전달하는 측과 해당 객체를 수신하는 측에서 사용하는 클래스 파일이 동일한지 체크하는 용도로 사용.
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
