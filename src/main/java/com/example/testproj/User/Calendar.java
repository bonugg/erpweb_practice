package com.example.testproj.User;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="T_MINI_CALENDAR")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long CALNO;
    @Column
    private String TITLE;
    @Column(name = "CALSTART")
    private String start;
    @Column(name = "CALEND")
    private String end;
    @Column(name = "DEPT")
    private String DEPT;
    @Column(name = "CLASSIFY")
    private String CLASSIFY;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NO")
    private User user;
}
