package com.revature.lmp1.services;


import com.revature.lmp1.daos.ReimbDAO;
import com.revature.lmp1.dtos.requests.NewReimbRequest;
import com.revature.lmp1.models.Reimbursement;
import com.revature.lmp1.utils.custom_exceptions.InvalidRequestException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;

public class ReimbService {

    private final ReimbDAO reimbDAO;

    public ReimbService(ReimbDAO reimbDAO){
        this.reimbDAO = reimbDAO;
    }

    public void newReimbursement(NewReimbRequest req){
        if(isValidType(req.getType())){
            //Need to implement token system in order to get user id
            //Reimbursement reimb = new Reimbursement(UUID.randomUUID().toString(),req.getAmount(),LocalDateTime.now(),null,req.getDescription(),null,null,);
        }
    }

    public List<Reimbursement> getPending(){
        List<Reimbursement> all = reimbDAO.getAllByStatus("pending");
        return all;
    }

    public boolean isValidStatus(String status){
        status = status.toLowerCase().trim();
        System.out.print(status);
        if(status.equals("approved") == false && status.equals("denied") == false && status.equals("pending") == false){
            throw new InvalidRequestException("\nInvalid Status! A reimbursement can only be (approved/denied/pending");
        }
        return true;
    }
    public boolean isValidType(String type){
        type = type.toLowerCase().trim();
        System.out.println(type);
        if(type.equals("lodging") == false && type.equals("travel") == false && type.equals("food") == false && type.equals("other") == false){
            throw new InvalidRequestException("\nInvalid Reimbursement Type! Reimbursements must belong to the category of (lodging/travel/food/other");
        }
        return true;
    }

}
