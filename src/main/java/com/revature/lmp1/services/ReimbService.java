package com.revature.lmp1.services;


import com.revature.lmp1.daos.ReimbDAO;
import com.revature.lmp1.daos.UserDAO;
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
    private final UserDAO userDAO;

    public ReimbService(ReimbDAO reimbDAO, UserDAO userDAO){
        this.reimbDAO = reimbDAO;
        this.userDAO = userDAO;
    }

    public Reimbursement newReimbursement(NewReimbRequest req, String id){
        Reimbursement reimb = null;
        if(isValidType(req.getType())){
            //Need to implement token system in order to get user id

            String type_id = reimbDAO.getTypeId(req.getType());
            reimb = new Reimbursement(UUID.randomUUID().toString(),req.getAmount(),LocalDateTime.now(),null,req.getDescription(),null,UUID.randomUUID().toString(),id,null,"1", type_id);
            System.out.println(reimb.toString());
            reimbDAO.save(reimb);


        }
        return reimb;
    }

    public List<Reimbursement> getPending(){
        return reimbDAO.getAllByStatus("pending");
    }

    public boolean isValidStatus(String status){
        status = status.toLowerCase().trim();
        System.out.print(status);
        if(!reimbDAO.getStatuses().contains(status)){
            throw new InvalidRequestException("\nInvalid Status! A reimbursement can only be (approved/denied/pending");
        }
        return true;
    }
    public boolean isValidType(String type){
        type = type.toLowerCase().trim();
        System.out.println(type);
        if(!reimbDAO.getTypes().contains(type)){
            throw new InvalidRequestException("\nInvalid Reimbursement Type! Reimbursements must belong to the category of (lodging/travel/food/other");
        }
        return true;
    }

}
