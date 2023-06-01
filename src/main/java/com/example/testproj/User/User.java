package com.example.testproj.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Enumerated(EnumType.STRING)
    @Column
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Calendar> calendarList = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserVacation> userVacationList = new ArrayList<>();

    public User(SessionUser user) {
        this.NO = user.getNO();
        this.DEPT = user.getDEPT();
        this.CNO = user.getCNO();
        this.NAME = user.getNAME();
        this.PWD = user.getPWD();
        this.EMAIL = user.getEMAIL();
        this.role = user.getRole();
    }
}
