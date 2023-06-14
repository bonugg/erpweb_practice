package com.example.testproj.repository;

import com.example.testproj.Clazz.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCNO(long CNO);
    List<User> findByDEPT(String DEPT);

    @Query(value = "SELECT COUNT(*) FROM T_MINI_MEMBER T WHERE T.CNO = :CNO", nativeQuery = true)
    int CnoCheck(long CNO);
}
