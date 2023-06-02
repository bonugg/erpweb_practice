package com.example.testproj.repository;

import com.example.testproj.Clazz.calendar.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    Optional<Calendar> findByVNO(long NO);
    Optional<Calendar> findByMNO(long NO);
    Optional<Calendar> findByBNO(long NO);
    List<Calendar> findByDEPT(String DEPT);
}
