package com.ehmjamiu.learn.service;

import com.ehmjamiu.learn.dto.ResetTokenResponse;
import org.springframework.stereotype.Service;

@Service
public interface ResetTokenService {
    ResetTokenResponse generateInitialPasswordReset(String username);
    void resetPassword(String resetPasswordToken, String newPassword);


}