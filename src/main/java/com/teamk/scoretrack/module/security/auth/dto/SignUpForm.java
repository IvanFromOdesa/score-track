package com.teamk.scoretrack.module.security.auth.dto;

public class SignUpForm {
    private String loginname;
    private String email;
    private String password;
    private boolean tosChecked;

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTosChecked() {
        return tosChecked;
    }

    public void setTosChecked(boolean tosChecked) {
        this.tosChecked = tosChecked;
    }
}
