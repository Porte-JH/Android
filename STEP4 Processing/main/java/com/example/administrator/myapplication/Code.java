package com.example.administrator.myapplication;

import java.io.Serializable;

public class Code implements Serializable {

    String grp, code, code_kor, desc, creation_date, processing_date;

    public Code(){

    }
    public Code(String grp, String code, String code_kor, String desc, String creation_date, String processing_date) {
        this.grp = grp;
        this.code = code;
        this.code_kor = code_kor;
        this.desc = desc;
        this.creation_date = creation_date;
        this.processing_date = processing_date;
    }

    public String getGrp() {
        return grp;
    }

    public void setGrp(String grp) {
        this.grp = grp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode_kor() {
        return code_kor;
    }

    public void setCode_kor(String code_kor) {
        this.code_kor = code_kor;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getProcessing_date() {
        return processing_date;
    }

    public void setProcessing_date(String processing_date) {
        this.processing_date = processing_date;
    }

    @Override
    public String toString() {
        return "Code{" +
                "grp='" + grp + '\'' +
                ", code='" + code + '\'' +
                ", code_kor='" + code_kor + '\'' +
                ", desc='" + desc + '\'' +
                ", creation_date='" + creation_date + '\'' +
                ", processing_date='" + processing_date + '\'' +
                '}';
    }
}
