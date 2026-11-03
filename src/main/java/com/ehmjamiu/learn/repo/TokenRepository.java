package com.ehmjamiu.learn.repo;

import com.ehmjamiu.learn.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

    Optional<Token> findTokenByUserUsername(String username);

    boolean existsByUserUsername(String userEmail);

    Optional<Token> findTokenByRefreshToken(String refreshToken);

    void deleteTokenByUserId(Integer user_id);
}
