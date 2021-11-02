package com.amr.gradutionPro.gradutionPro.Model;


import com.amr.gradutionPro.gradutionPro.Service;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Turn")
@Setter
@Getter
public class Turn {
    @Id
    @GeneratedValue
    @Column(name = "TurnId")
    private long id;
    @Column(name = "clientId")

    private long clientId;
    @Column(name = "service")
    private Service service;

    @Column(name = "estimatedTimeToStart")

    private int estimatedTimeToStart;

    @Column(name = "timeWhenPicked")
    private LocalTime time;

    @Column(name = "Date")
    private LocalDate date;
    @Column(name = "windowSelected")

    private int windowSelected;
    @Column(name ="estimatedTime" )
    private int estimatedTime;

    public Turn(long clientId, Service service, int estimatedTimeToStart, LocalTime time, LocalDate date, int windowSelected, int estimatedTime) {
        this.clientId = clientId;
        this.service = service;
        this.estimatedTimeToStart = estimatedTimeToStart;
        this.time = time;
        this.date = date;
        this.windowSelected = windowSelected;
        this.estimatedTime = estimatedTime;
    }

    public Turn() {
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
}
