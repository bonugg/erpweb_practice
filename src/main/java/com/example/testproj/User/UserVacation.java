package com.example.testproj.User;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="T_MINI_MEMBER_VACATION")
public class UserVacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long VNO;
    @Column
    private String DEPT;
    @Column
    private long CNO;
    @Column
    private String NAME;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Calendar> calendarList = new ArrayList<>();
}
