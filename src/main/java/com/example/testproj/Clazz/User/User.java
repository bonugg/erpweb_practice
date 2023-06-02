package com.example.testproj.Clazz.User;

import com.example.testproj.Clazz.Approval.Business;
import com.example.testproj.Clazz.calendar.Calendar;
import com.example.testproj.Clazz.Approval.Meeting;
import com.example.testproj.Clazz.Approval.Vacation;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "T_MINI_MEMBER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long NO;
    @Column
    private String DEPT;
    @Column(unique = true) //중복 제거
    private long CNO;
    @Column
    private String NAME;
    @Column
    private String PWD;
    @Column
    private String EMAIL;
    @Column
    private String POSITION;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Calendar> calendarList = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Vacation> vacationList = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Meeting> meetingList = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Business> businessList = new ArrayList<>();

    public User(SessionUser user) {
        this.NO = user.getNO();
        this.DEPT = user.getDEPT();
        this.CNO = user.getCNO();
        this.NAME = user.getNAME();
        this.PWD = user.getPWD();
        this.EMAIL = user.getEMAIL();
    }
}
