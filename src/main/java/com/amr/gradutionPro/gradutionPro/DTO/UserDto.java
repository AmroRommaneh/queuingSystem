package com.amr.gradutionPro.gradutionPro.DTO;

import com.amr.gradutionPro.gradutionPro.Role;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.*;

public class UserDto {


    private long id;
    private String name;
    private String email;
    private int phoneNumber;
    private String password;
    private Role role;

    public void setId(long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
