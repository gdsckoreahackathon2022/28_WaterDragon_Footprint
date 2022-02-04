package com.nohorang.suryongfootprint.repository;

import com.nohorang.suryongfootprint.model.Challenge;
import com.nohorang.suryongfootprint.model.Count;
import com.nohorang.suryongfootprint.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.http.WebSocket;
import java.util.List;
import java.util.Optional;

public interface CountRepository extends JpaRepository<Count, Integer> {
    Optional<Count> findByUserAndChallenge(User user, Challenge challenge);
    List<Count> findByUser(User user);
}
