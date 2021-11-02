package com.amr.gradutionPro.gradutionPro.Model;

import javax.persistence.*;

@Entity
@Table(name = "Feedback")
public class Feedback {
    @Id
    @GeneratedValue
    @Column(name = "feedbackId")
    private long id;
    @Column(name = "clientId")

    private long clientId;
    @Column(name = "descripiton" )
    private String description;

    public Feedback(long clientId, String description) {
        this.clientId = clientId;
        this.description = description;
    }

    public Feedback() {
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
