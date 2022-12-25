package com.example.push1_1;

public class SignPageItems {

    String usingName;
    String email;
    String loginId;
    String loginPasword;

    public String getUsingName() {
        return usingName;
    }

    public void setUsingName(String usingName) {

        this.usingName = usingName;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getLoginId() {

        return loginId;
    }

    public void setLoginId(String loginId) {

        this.loginId = loginId;
    }

    public String getLoginPasword() {

        return loginPasword;
    }

    public void setLoginPasword(String loginPasword) {

        this.loginPasword = loginPasword;
    }

    public SignPageItems(String usingName, String email, String loginId, String loginPasword) {
        this.usingName = usingName;
        this.email = email;
        this.loginId = loginId;
        this.loginPasword = loginPasword;
    }
}
