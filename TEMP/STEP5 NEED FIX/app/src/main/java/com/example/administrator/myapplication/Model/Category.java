package com.example.administrator.myapplication.Model;
import java.io.Serializable;

public class Category implements Serializable {

    String grp, code, codekor, caption, creationdate, processingdate;

    public Category(){}

    public Category(String grp, String code, String codekor, String caption, String creationdate, String processingdate) {
        this.grp = grp;
        this.code = code;
        this.codekor = codekor;
        this.caption = caption;
        this.creationdate = creationdate;
        this.processingdate = processingdate;
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

    public String getCodekor() {
        return codekor;
    }

    public void setCodekor(String codekor) {
        this.codekor = codekor;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }

    public String getProcessingdate() {
        return processingdate;
    }

    public void setProcessingdate(String processingdate) {
        this.processingdate = processingdate;
    }

    @Override
    public String toString() {
        return "Category{" +
                "grp='" + grp + '\'' +
                ", code='" + code + '\'' +
                ", codekor='" + codekor + '\'' +
                ", caption='" + caption + '\'' +
                ", creationdate='" + creationdate + '\'' +
                ", processingdate='" + processingdate + '\'' +
                '}';
    }
}