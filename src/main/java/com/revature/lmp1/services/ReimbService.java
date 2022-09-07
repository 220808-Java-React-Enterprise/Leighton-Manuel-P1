package com.revature.lmp1.services;


import com.revature.lmp1.daos.ReimbDAO;
import com.revature.lmp1.daos.UserDAO;
import com.revature.lmp1.dtos.requests.NewReimbRequest;
import com.revature.lmp1.dtos.requests.PendingReimbRequest;
import com.revature.lmp1.dtos.requests.ReimbHistoryRequest;
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
        return reimbDAO.getAllByStatus("pending");
    }

    public List<Reimbursement> getAllByResolver(String resolver_id){
        List<Reimbursement> all = reimbDAO.getAllByResolver(resolver_id);
        return all;
    }

    public void changeReimbStatus(ReimbStatusRequest req, String id){
        System.out.print(req.getCurrentStatus());
        if(isValidStatus(req.getCurrentStatus())) {
            reimbDAO.changeReimbStatus(req.getId(), reimbDAO.getStatusId(req.getCurrentStatus()),id);
        }
    }

    public boolean isValidStatus(String status){
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

    public List<Reimbursement> getHistory(ReimbHistoryRequest request) {
        String id = request.getId();
        String status = request.getStatus().toLowerCase().trim();
        String sort = request.getDate().toLowerCase().trim();
        String order = request.getOrder().toUpperCase().trim();

        if(reimbDAO.getStatuses().contains(status)) {
            if(sort.equals("submitted") || sort.equals("resolved") || sort.equals("amount")) {
                if(order.equals("ASC") || order.equals("DESC")) {
                    return reimbDAO.getReimbursementHistory(id, status, sort, order);
                } else {
                    throw new InvalidRequestException("\nOrder not recognized, please enter either ASC for ascending or DESC for descending");
                }
            } else {
                throw new InvalidRequestException(("\nSort not recognized, pleased sort by either submitted, resolved, or amount"));
            }
        } else {
            throw new InvalidRequestException("\nInvalid Status! A reimbursement can only be (approved/denied/pending");
        }
    }

    public List<Reimbursement> getUserPending(PendingReimbRequest request) {
        return reimbDAO.getPendingReimbursementsByUser(request.getId());
    }
}
