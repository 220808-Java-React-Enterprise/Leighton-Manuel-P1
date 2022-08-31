package com.revature.lmp1.services;


import com.revature.lmp1.daos.ReimbDAO;
import com.revature.lmp1.models.Reimbursement;

import java.util.List;

public class ReimbService {

    private final ReimbDAO reimbDAO;

    public ReimbService(ReimbDAO reimbDAO){
        this.reimbDAO = reimbDAO;
    }

    public List<Reimbursement> getPending(){
        List<Reimbursement> all = reimbDAO.getAllByStatus("pending");
        return all;
    }

}
