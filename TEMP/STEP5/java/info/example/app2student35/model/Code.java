package info.example.app2student35.model;

import java.io.Serializable;

public class Code implements Serializable {

    private String grp;
    private String code;
    private String codeKor;
    private String desc;
    private String creation_date;
    private String processing_date;

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
                ", codeKor='" + codeKor + '\'' +
                ", desc='" + desc + '\'' +
                ", creation_date='" + creation_date + '\'' +
                ", processing_date='" + processing_date + '\'' +
                '}';
    }
}