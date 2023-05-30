package com.example.testproj.User;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="T_MINI_MEMBER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long NO;
    @Column
    private String DEPT;
    @Column
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
}
