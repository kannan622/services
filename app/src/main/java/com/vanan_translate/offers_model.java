package com.vanan_translate;

public class offers_model {


    private String id;
    private String language_name;
    private String language_code;
    private String flag;


    public offers_model(String id, String language_name, String language_code, String flag) {
        // TODO Auto-generated constructor stub


        this.id = id;
        this.language_name = language_name;
        this.language_code = language_code;
        this.flag = flag;

    }


    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }


    public String getlanguage_name() {
        return language_name;
    }

    public void setlanguage_name(String language_name) {
        this.language_name = language_name;
    }

    public String getlanguage_code() {
        return language_code;
    }

    public void setlanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public String getflag() {
        return flag;
    }

    public void setflag(String flag) {
        this.flag = flag;
    }


}
