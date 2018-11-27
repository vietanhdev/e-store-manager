package com.example.store.payload.common.response;

public class JwtAuthenticationResponse {
    private boolean success;
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(Boolean success, String accessToken) {
        this.success = success;
        this.accessToken = accessToken;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}