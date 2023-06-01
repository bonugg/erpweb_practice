package com.example.testproj.repository;


import com.example.testproj.User.UserVacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserVacationRepository extends JpaRepository<UserVacation, Long> {
    UserVacation findByVNO(long VNO);
    @Query("SELECT T, U.CNO FROM UserVacation T JOIN FETCH User U ON T.user = U WHERE U.DEPT =:DEPT ORDER BY T.VNO DESC")
    List<Object[]> findByDeptList(@Param("DEPT") String DEPT);

    @Query("SELECT T, U FROM UserVacation T JOIN FETCH User U ON T.user = U WHERE T.VNO =:VNO")
    Object findByVNOView(@Param("VNO") long VNO);
}
