package com.ehmjamiu.learn.repo;

import com.ehmjamiu.learn.entity.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {

    Optional<ResetToken> findByUserUsername(String userUsername);

    Optional<ResetToken> findByToken(UUID token);
}