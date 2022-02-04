package com.nohorang.suryongfootprint.repository;

import com.nohorang.suryongfootprint.model.Challenge;
import com.nohorang.suryongfootprint.model.Post;
import com.nohorang.suryongfootprint.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer>{
    List<Post> findByUser(User user);
    List<Post> findByChallenge(Challenge challenge);
    List<Post> findByUserAndChallenge(User user, Challenge challenge);
    List<Post> findByUserAndChallengeAndState(User user, Challenge challenge, int state);
}
