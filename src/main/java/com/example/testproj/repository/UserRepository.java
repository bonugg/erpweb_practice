package com.example.testproj.repository;

import com.example.testproj.Clazz.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCNO(long CNO);

    @Query(value = "SELECT COUNT(*) FROM T_MINI_MEMBER T WHERE T.CNO = :CNO", nativeQuery = true)
    int CnoCheck(long CNO);
}
