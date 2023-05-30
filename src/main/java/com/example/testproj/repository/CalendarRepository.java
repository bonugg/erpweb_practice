package com.example.testproj.repository;

import com.example.testproj.User.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    List<Calendar> findByDEPT(String DEPT);
}
