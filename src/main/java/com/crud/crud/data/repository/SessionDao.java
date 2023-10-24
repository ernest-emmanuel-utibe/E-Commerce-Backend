package com.crud.crud.data.repository;

import com.crud.crud.data.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionDao extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findByToken(String token);
    Optional<UserSession> findByUserId(Integer userId);
}
