package com.example.step5.Model;

import java.io.Serializable;

public class Category implements Serializable {

    String grp, code, codeKor, caption, creation_date, processing_date;

    public Category(){}

    public Category(String grp, String code, String codeKor, String caption, String creation_date, String processing_date) {
        this.grp = grp;
        this.code = code;
        this.codeKor = codeKor;
        this.caption = caption;
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

    public String getCodeKor() {
        return codeKor;
    }

    public void setCodeKor(String codeKor) {
        this.codeKor = codeKor;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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
        return "Category{" +
                "grp='" + grp + '\'' +
                ", code='" + code + '\'' +
                ", codeKor='" + codeKor + '\'' +
                ", caption='" + caption + '\'' +
                ", creation_date='" + creation_date + '\'' +
                ", processing_date='" + processing_date + '\'' +
                '}';
    }
}
