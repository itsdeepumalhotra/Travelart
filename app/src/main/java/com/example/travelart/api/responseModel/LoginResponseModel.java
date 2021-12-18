package com.example.travelart.api.responseModel;

public class LoginResponseModel {
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoginResponseModel() {
    }

    public LoginResponseModel(String status) {
        this.status = status;
    }
}
