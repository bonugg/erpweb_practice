package com.example.testproj.repository;


import com.example.testproj.Clazz.Approval.Vacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
    Vacation findByVNO(long VNO);
    @Query("SELECT T, U FROM Vacation T JOIN FETCH User U ON T.user = U WHERE U.DEPT =:DEPT ORDER BY T.VNO DESC")
    Page<Object[]> findByDeptList(@Param("DEPT") String DEPT, Pageable pageable);

    @Query("SELECT T, U.CNO FROM Vacation T JOIN FETCH User U ON T.user = U WHERE U.NO =:NO ORDER BY T.VNO DESC")
    List<Object[]> findByNOList(@Param("NO") long NO);

    @Query("SELECT T, U FROM Vacation T JOIN FETCH User U ON T.user = U WHERE T.VNO =:VNO")
    Object findByVNOView(@Param("VNO") long VNO);

}
