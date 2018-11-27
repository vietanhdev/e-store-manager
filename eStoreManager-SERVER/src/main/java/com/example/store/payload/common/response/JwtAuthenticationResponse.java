package com.example.store.payload.common.response;

public class JwtAuthenticationResponse {
    private boolean success;
    private Long id;
    private String name;
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(Boolean success, Long id, String name, String accessToken) {
        this.success = success;
        this.accessToken = accessToken;
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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