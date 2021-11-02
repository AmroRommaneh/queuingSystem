package com.amr.gradutionPro.gradutionPro.DTO;

import com.amr.gradutionPro.gradutionPro.Service;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class TurnDto {


    private long clientId;
    private Service srvice;
    private int arrivingTime;



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



    public int getArrivingTime() {
        return arrivingTime;
    }

    public void setArrivingTime(int arrivingTime) {
        this.arrivingTime = arrivingTime;
    }
}
