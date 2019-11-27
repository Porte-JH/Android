package info.example.app2student35.model;

import java.io.Serializable;

public class Student implements Serializable {

    String hakbun, name, phone, imageName, hakgoa;

    public Student() {
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

    @Override
    public String toString() {
        return "Student{" +
                "hakbun='" + hakbun + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", imageName='" + imageName + '\'' +
                ", hakgoa='" + hakgoa + '\'' +
                '}';
    }
}
