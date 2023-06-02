package com.example.testproj.repository;


import com.example.testproj.Clazz.Approval.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    Meeting findByVNO(long VNO);
    @Query("SELECT T, U.CNO FROM Meeting T JOIN FETCH User U ON T.user = U WHERE U.DEPT =:DEPT ORDER BY T.VNO DESC")
    List<Object[]> findByDeptList(@Param("DEPT") String DEPT);

    @Query("SELECT T, U.CNO FROM Meeting T JOIN FETCH User U ON T.user = U WHERE U.NO =:NO ORDER BY T.VNO DESC")
    List<Object[]> findByNOList(@Param("NO") long NO);

    @Query("SELECT T, U FROM Meeting T JOIN FETCH User U ON T.user = U WHERE T.VNO =:VNO")
    Object findByVNOView(@Param("VNO") long VNO);
}
