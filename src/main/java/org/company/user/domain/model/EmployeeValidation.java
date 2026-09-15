package org.company.user.domain.model;

public class EmployeeValidation {

    private String email;
    private String password;

    public EmployeeValidation() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
