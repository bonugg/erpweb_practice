package com.example.testproj.User;

import lombok.Data;

@Data
public class SessionUser {
    private long NO;

    private String DEPT;

    private long CNO;

    private String NAME;

    private String PWD;

    private String EMAIL;

    private Role role;

    public SessionUser(User user){
        this.NO = user.getNO();
        this.DEPT = user.getDEPT();
        this.CNO = user.getCNO();
        this.NAME = user.getNAME();
        this.PWD = user.getPWD();
        this.EMAIL = user.getEMAIL();
        this.role = user.getRole();
    }
}

