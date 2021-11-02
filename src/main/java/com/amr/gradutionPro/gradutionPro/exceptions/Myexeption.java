package com.amr.gradutionPro.gradutionPro.exceptions;

public class Myexeption extends RuntimeException  {
    private static final long serialVersionUID = -7806029002430564887L;

    private String message;
    public Myexeption(String message) {
this.message=message;
    }

    public Myexeption() {
    }

    public void setErrorMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return message;
    }


}