package com.example.administrator.myapplication.Model;

import java.io.Serializable;

public class Code implements Serializable {

    private String grp;
    private String code;
    private String codeKor;
    private String desc;
    private String creationDate;
    private String processingDate;

    public Code() {
    }

    public String getGrp() {
        return grp;
    }

    public Code setGrp(String grp) {
        this.grp = grp;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Code setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Code setDesc(String remarks) {
        this.desc = remarks;
        return this;
    }

    public String getCodeKor() {
        return codeKor;
    }

    public Code setCodeKor(String codeKor) {
        this.codeKor = codeKor;
        return this;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(String processingDate) {
        this.processingDate = processingDate;
    }

    @Override
    public String toString() {
        return "Code{" +
                "grp='" + grp + '\'' +
                ", code='" + code + '\'' +
                ", codeKor='" + codeKor + '\'' +
                ", desc='" + desc + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", processingDate='" + processingDate + '\'' +
                '}';
    }
}