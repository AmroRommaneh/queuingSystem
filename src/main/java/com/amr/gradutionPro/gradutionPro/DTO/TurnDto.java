package com.amr.gradutionPro.gradutionPro.DTO;

import com.amr.gradutionPro.gradutionPro.Service;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

public class TurnDto {

private long id;
    private long clientId;
    private Service srvice;
    private int arrivingTime;
    private int estimatedTime;
    private int additonalTime;

    public int getAdditonalTime() {
        return additonalTime;
    }

    public void setAdditonalTime(int additonalTime) {
        this.additonalTime = additonalTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public Service getSrvice() {
        return srvice;
    }

    public void setSrvice(Service srvice) {
        this.srvice = srvice;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public int getArrivingTime() {
        return arrivingTime;
    }

    public void setArrivingTime(int arrivingTime) {
        this.arrivingTime = arrivingTime;
    }
}
