package com.ehmjamiu.learn.service.Impl;

import com.ehmjamiu.learn.dto.ResetTokenResponse;
import com.ehmjamiu.learn.entity.ResetToken;
import com.ehmjamiu.learn.entity.TodoUser;
import com.ehmjamiu.learn.exceptionHandler.CustomBadRequest;
import com.ehmjamiu.learn.exceptionHandler.UserDoesNotExist;
import com.ehmjamiu.learn.repo.ResetTokenRepository;
import com.ehmjamiu.learn.repo.TodoUserRepository;
import com.ehmjamiu.learn.service.ResetTokenService;
import com.ehmjamiu.learn.util.CalculateExpiresTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResetTokenServiceImpl implements ResetTokenService {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final TodoUserRepository todoUserRepository;
    private final ResetTokenRepository resetTokenRepository;

    public ResetTokenServiceImpl(TodoUserRepository todoUserRepository, ResetTokenRepository resetTokenRepository) {
        this.todoUserRepository = todoUserRepository;
        this.resetTokenRepository = resetTokenRepository;
    }

    @Override
    public ResetTokenResponse generateInitialPasswordReset(String username) {
        TodoUser user = todoUserRepository.findByUsername(username).orElseThrow(()
                ->new UserDoesNotExist("User not found"));
        String token = UUID.randomUUID().toString();
        Optional<ResetToken> existingResetToken = resetTokenRepository.findByUserUsername(username);
        if (existingResetToken.isPresent()) {
            ResetToken resetToken = existingResetToken.get();
            return new ResetTokenResponse(resetToken.getToken(), CalculateExpiresTime.calculateExpiresIn(resetToken.getExpiresAt()),resetToken.getUser().getUsername());
        }
        ResetToken resetToken = new ResetToken();
        resetToken.setToken(UUID.fromString(token));
        resetToken.setUser(user);
        resetToken.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        resetTokenRepository.save(resetToken);
        return new ResetTokenResponse(resetToken.getToken(),CalculateExpiresTime.calculateExpiresIn(resetToken.getExpiresAt()),user.getUsername());

    }

    @Override
    public void resetPassword(String resetPasswordToken, String newPassword) {
        ResetToken resetToken=resetTokenRepository.findByToken(UUID.fromString(resetPasswordToken)).orElseThrow(()
                ->new CustomBadRequest("Invalid or expired reset token"));
        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new CustomBadRequest("Reset token expired");
        }
        resetToken.getUser().setPassword(encoder.encode(newPassword));
        resetTokenRepository.delete(resetToken);

    }
}