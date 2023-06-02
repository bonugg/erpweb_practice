package com.example.testproj.Clazz.calendar;

import com.example.testproj.Clazz.User.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Component
@Table(name="T_MINI_CALENDAR")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long CALNO;
    @Column
    private String TITLE;
    @Column
    private String DESCRIPTION;
    @Column(name = "CALSTART")
    private String start;
    @Column(name = "CALEND")
    private String end;
    @Column(name = "DEPT")
    private String DEPT;
    @Column(name = "CLASSIFY")
    private String CLASSIFY;
    @Column(name = "VACATIONTYPE")
    private String VACATIONTYPE;
    @Column(name = "VNO")
    private long VNO;
    @Column(name = "MNO")
    private long MNO;
    @Column(name = "BNO")
    private long BNO;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NO")
    private User user;

    public Calendar(String TITLE, String DESCRIPTION, String start, String end, String DEPT, String CLASSIFY, long NO, User user) {
        if(CLASSIFY.equals("회의")){
            this.TITLE = TITLE;
            this.DESCRIPTION = DESCRIPTION;
            this.start = start;
            this.end = end;
            this.DEPT = DEPT;
            this.CLASSIFY = CLASSIFY;
            this.MNO = NO;
            this.user = user;
        }else if(CLASSIFY.equals("출장")){
            this.TITLE = TITLE;
            this.DESCRIPTION = DESCRIPTION;
            this.start = start;
            this.end = end;
            this.DEPT = DEPT;
            this.CLASSIFY = CLASSIFY;
            this.BNO = NO;
            this.user = user;
        }
    }

    public Calendar(String TITLE, String DESCRIPTION, String start, String end, String DEPT, String CLASSIFY, String VACATIONTYPE, long VNO, User user) {
        this.TITLE = TITLE;
        this.DESCRIPTION = DESCRIPTION;
        this.start = start;
        this.end = end;
        this.DEPT = DEPT;
        this.CLASSIFY = CLASSIFY;
        this.VACATIONTYPE = VACATIONTYPE;
        this.VNO = VNO;
        this.user = user;
    }
}
