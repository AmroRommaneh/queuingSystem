package com.amr.gradutionPro.gradutionPro.Model;

import javax.persistence.*;

@Entity
@Table(name = "serviceProvider")
public class Service_provider {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "password")
    private String password;

    public Service_provider( String password) {

        this.password = password;
    }

    public Service_provider() {

    }

    public long getId() {
        return id;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
