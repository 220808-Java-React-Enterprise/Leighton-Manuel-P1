package com.revature.lmp1.services;


import com.revature.lmp1.daos.ReimbDAO;
import com.revature.lmp1.daos.UserDAO;
import com.revature.lmp1.dtos.requests.NewReimbRequest;
import com.revature.lmp1.dtos.requests.ReimbStatusRequest;
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
        List<Reimbursement> all = reimbDAO.getAllByStatus("pending");
        return all;
    }

    public void changeReimbStatus(ReimbStatusRequest req){
        System.out.print(req.getStatus());
        if(isValidStatus(req.getStatus())) {
            reimbDAO.changeReimbStatus(req.getId(), reimbDAO.getStatusId(req.getStatus()));
        }
    }

    public boolean isValidStatus(String status){
        System.out.print(status);
        status = status.toLowerCase().trim();
        if(status.equals("approved") == false && status.equals("denied") == false && status.equals("pending") == false){
            throw new InvalidRequestException("\nInvalid Status! A reimbursement can only be (approved/denied/pending");
        }
        return true;
    }
    public boolean isValidType(String type){
        type = type.toLowerCase().trim();
        if(type.equals("lodging") == false && type.equals("travel") == false && type.equals("food") == false && type.equals("other") == false){
            throw new InvalidRequestException("\nInvalid Reimbursement Type! Reimbursements must belong to the category of (lodging/travel/food/other");
        }
        return true;
    }

}
