package com.amr.gradutionPro.gradutionPro.Model;

import javax.persistence.*;

@Entity
@Table(name = "Notifications")
public class Notification {
    @Id
    @GeneratedValue
    @Column(name = "notificationId")
    private long id;
    @Column(name = "clientid")
    private long clientId;
    @Column(name = "deccription")
    private String description;

    public Notification(long clientId, String description) {
        this.clientId = clientId;
        this.description = description;
    }

    public Notification() {

    }

    public long getId() {
        return id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
