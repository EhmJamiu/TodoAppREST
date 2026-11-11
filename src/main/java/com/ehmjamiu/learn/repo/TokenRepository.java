package com.ehmjamiu.learn.repo;

import com.ehmjamiu.learn.entity.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

    Optional<Token> findTokenByUserUsername(String username);

    boolean existsByUserUsername(String userEmail);

    Optional<Token> findTokenByRefreshToken(String refreshToken);

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM Token t WHERE t.user.id = :userId""")
    void deleteTokenByUserId(@Param("userId")Integer userid);
}
