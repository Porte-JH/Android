package info.example.app2student36.model;

import java.io.Serializable;

public class Student implements Serializable {

    String hakbun, name, phone, imageName,
            hakgoa, haknyeon, ban, incollege, mento,
            created_at, updated_at;

    public Student() {
    }

    public String getHakbun() {
        return hakbun;
    }

    public Student setHakbun(String hakbun) {
        this.hakbun = hakbun;
        return this;
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Student setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getImageName() {
        return imageName;
    }

    public Student setImageName(String imageName) {
        this.imageName = imageName;
        return this;
    }

    public String getHakgoa() {
        return hakgoa;
    }

    public Student setHakgoa(String hakgoa) {
        this.hakgoa = hakgoa;
        return this;
    }

    public String getHaknyeon() {
        return haknyeon;
    }

    public Student setHaknyeon(String haknyeon) {
        this.haknyeon = haknyeon;
        return this;
    }

    public String getBan() {
        return ban;
    }

    public void setBan(String ban) {
        this.ban = ban;
    }

    public String getIncollege() {
        return incollege;
    }

    public void setIncollege(String incollege) {
        this.incollege = incollege;
    }



    public String getCreatedAt() {
        return created_at;
    }

    public Student setCreatedAt(String created_at) {
        this.created_at = created_at;
        return this;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public Student setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
        return this;
    }

    public String getMento() {
        return mento;
    }
    public String getMentoKor() {

        String mentoKor = null;
        if(mento.equals("1")) mentoKor = "이형일";
        else if(mento.equals("2"))  mentoKor = "김인범";
        else if(mento.equals("3"))  mentoKor = "김재생";
        else if(mento.equals("4")) mentoKor = "박호균";


        return mentoKor;
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
                ", haknyeon='" + haknyeon + '\'' +
                ", ban='" + ban + '\'' +
                ", incollege='" + incollege + '\'' +
                ", mento='" + mento + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
