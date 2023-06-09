package com.example.testproj.repository;

import com.example.testproj.Clazz.calendar.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    Optional<Calendar> findByVNO(long NO);
    Optional<Calendar> findByMNO(long NO);
    Optional<Calendar> findByBNO(long NO);
    List<Calendar> findByDEPT(String DEPT);

    @Query(value = "SELECT T.CALSTART FROM T_MINI_CALENDAR T WHERE T.CLASSIFY = '출퇴근' AND T.NO =:no AND T.CALSTART LIKE %:start%", nativeQuery = true)
    String findByCLASSIFYANDSTART(long no, String start);

    @Query(value = "SELECT T.CALEND FROM T_MINI_CALENDAR T WHERE T.CLASSIFY = '출퇴근' AND T.NO =:no AND T.CALEND LIKE %:end%", nativeQuery = true)
    String findByCLASSIFYANDEND(long no, String end);

    @Query(value = "SELECT * FROM T_MINI_CALENDAR T WHERE T.CLASSIFY = '출퇴근' AND T.NO =:no AND T.CALSTART LIKE %:start%", nativeQuery = true)
    Optional<Calendar> findByCLASSIFYANDSTARTy(long no, String start);
}
