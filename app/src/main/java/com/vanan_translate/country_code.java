package com.vanan_translate;

public class country_code {
    private String id;
    private String name;

    public country_code(String name, String id) {
        this.id = id;
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //to display object as a string in spinner
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof country_code) {
            country_code c = (country_code) obj;
            if (c.getName().equals(name) && c.getId() == id) return true;
        }

        return false;
    }
}
