package com.nohorang.suryongfootprint.repository;

import com.nohorang.suryongfootprint.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByUserNameAndUserEmail(String user_name,String user_email);
    Optional<User> findByUserIdAndUserNameAndUserEmail(String user_id, String user_name, String user_email);

    Optional<User> findByUserNickname(String user_nickname);
    Optional<User> findByUserEmail(String user_email);
}
