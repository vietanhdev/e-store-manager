package com.example.store.payload;

import javax.validation.constraints.*;

public class ChangePasswordRequest {

    @NotBlank
    @Size(max = 20)
    private String oldPassword;

    @NotBlank
    @Size(max = 20)
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}