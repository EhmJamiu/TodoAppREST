package com.ehmjamiu.learn.service;

import com.ehmjamiu.learn.dto.ResetTokenResponse;

public interface ResetTokenService {
    ResetTokenResponse generateInitialPasswordReset(String username);
    void resetPassword(String resetPasswordToken, String newPassword);


}