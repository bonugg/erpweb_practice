package com.example.testproj.Clazz.Approval;

import com.example.testproj.Clazz.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="T_MINI_BUSINESS")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long VNO;
    @Column
    private String TITLE;
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
    @Column
    private String CANCLEREASON;
    @Column
    private String APPROVER;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NO")
    private User user;
}
