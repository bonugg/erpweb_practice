package com.example.testproj.repository;

import com.example.testproj.User.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    Optional<Calendar> findByVNO(long VNO);
    List<Calendar> findByDEPT(String DEPT);
}
