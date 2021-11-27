package com.amr.gradutionPro.gradutionPro.Model;

import com.amr.gradutionPro.gradutionPro.Provider;
import com.amr.gradutionPro.gradutionPro.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
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
    @Email
    @Column(name = "email")
    private String email;
    @Column(name = "phoneNumber")
    private int phoneNumber;
    @Column(name = "password")
    private String password;
    @Column(name = "Role")
    private Role role;
    @Enumerated(EnumType.STRING)
    private Provider provider;


    public User( String name, String email, int phoneNumber, String password,Role role) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role=role;
    }
    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public void setPassword(String password) {
        this.password=password;
    }
}
