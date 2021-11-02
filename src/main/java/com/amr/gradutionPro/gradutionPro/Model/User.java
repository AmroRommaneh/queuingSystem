package com.amr.gradutionPro.gradutionPro.Model;

import com.amr.gradutionPro.gradutionPro.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Setter @Getter @NoArgsConstructor
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "UserId")
    private long id;
    @Column(name = "UserName")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "phoneNumber")
    private int phoneNumber;
    @Column(name = "password")
    private String password;
    @Column(name = "Role")
    private Role role;

    public User( String name, String email, int phoneNumber, String password,Role role) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role=role;
    }

    public void setPassword(String password) {
        this.password=password;
    }
}
