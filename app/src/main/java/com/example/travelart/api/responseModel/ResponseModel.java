package com.example.travelart.api.responseModel;

public class ResponseModel {
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResponseModel() {
    }

    public ResponseModel(String status) {
        this.status = status;
    }
}
