package com.example.step4_test.Model;

import java.io.Serializable;

public class Category implements Serializable {

    String grp, code, codekor;

    public Category(){}

    public Category(String grp, String code, String codekor) {
        this.grp = grp;
        this.code = code;
        this.codekor = codekor;
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

    @Override
    public String toString() {
        return "Category{" +
                "grp='" + grp + '\'' +
                ", code='" + code + '\'' +
                ", codekor='" + codekor + '\'' +
                '}';
    }
}
