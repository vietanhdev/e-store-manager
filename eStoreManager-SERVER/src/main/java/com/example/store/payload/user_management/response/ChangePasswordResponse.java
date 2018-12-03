package com.example.store.payload.user_management.response;

public class ChangePasswordResponse {
    public String tokenType = "Bearer";
    public String accessToken;

    public ChangePasswordResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
