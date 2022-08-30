package com.revature.lmp1.services;


import com.revature.lmp1.daos.ReimbDAO;

public class ReimbService {

    private final ReimbDAO reimbDAO;

    public ReimbService(ReimbDAO reimbDAO){
        this.reimbDAO = reimbDAO;
    }

}
