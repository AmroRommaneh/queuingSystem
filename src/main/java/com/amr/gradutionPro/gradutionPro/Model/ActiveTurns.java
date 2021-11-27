package com.amr.gradutionPro.gradutionPro.Model;


import com.amr.gradutionPro.gradutionPro.Service;
import com.amr.gradutionPro.gradutionPro.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ActiveTurn")
@Setter
@Getter
public class ActiveTurns {
    @Id
    @GeneratedValue
    @Column(name = "ActiveTurnId")
    private long id;
    @Column(name = "ActiveTurnclientId")

    private long clientId;
    @Column(name = "ActiveTurnservice")
    private Service service;

    @Column(name = "ActiveTurnestimatedTimeToStart")

    private int estimatedTimeToStart;

    @Column(name = "ActiveTurntimeWhenPicked")
    private LocalTime time;

    @Column(name = "ActiveTurnDate")
    private LocalDate date;
    @Column(name = "ActiveTurnwindowSelected")

    private int windowSelected;
    @Column(name ="ActiveTurnestimatedTime" )
    private int estimatedTime;
    @Column(name = "Status")
    private Status status;

    public ActiveTurns(long clientId, Service service, int estimatedTimeToStart, LocalTime time, LocalDate date, int windowSelected, int estimatedTime,Status status) {
        this.clientId = clientId;
        this.service = service;
        this.estimatedTimeToStart = estimatedTimeToStart;
        this.time = time;
        this.date = date;
        this.windowSelected = windowSelected;
        this.estimatedTime = estimatedTime;
        this.status=status;

    }

    public ActiveTurns() {
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public int getEstimatedTimeToStart() {
        return estimatedTimeToStart;
    }

    public void setEstimatedTimeToStart(int estimatedTimeToStart) {
        this.estimatedTimeToStart = estimatedTimeToStart;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getWindowSelected() {
        return windowSelected;
    }

    public void setWindowSelected(int windowSelected) {
        this.windowSelected = windowSelected;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
