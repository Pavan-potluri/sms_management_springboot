package com.pod5.sms_management.repository;

import com.pod5.sms_management.model.CurrentUserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepo extends JpaRepository<CurrentUserSession, String> {

    public Optional<CurrentUserSession> findByUuId(String UuId);

}
