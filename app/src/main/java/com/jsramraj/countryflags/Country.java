package com.jsramraj.countryflags;

public class Country {
    private String name;
    private String dialCode;
    private String code;

    public String getName() {
        return name;
    }

    public String getDialCode() {
        return dialCode;
    }

    public String getCode() {
        return code;
    }

    public Country(String name, String dialCode, String code) {
        this.name = name;
        this.dialCode = dialCode;
        this.code = code;
    }
}
