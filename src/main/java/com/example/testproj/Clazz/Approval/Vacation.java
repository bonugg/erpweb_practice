package com.example.testproj.Clazz.Approval;

import com.example.testproj.Clazz.User.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="T_MINI_VACATION")
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long VNO;
    @Column
    private String TITLE;
    @Column(name = "VACATIONTYPE")
    private String VACATIONTYPE;
    @Column(name = "CALSTART")
    private String start;
    @Column(name = "CALEND")
    private String end;
    @Column
    private String DESCRIPTION;
    @ColumnDefault("'미승인'")
    private String Accessva ;
    @Column
    private String CLASSIFY;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NO")
    private User user;
}
