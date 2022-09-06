package com.revature.lmp1.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lmp1.dtos.requests.NewReimbRequest;
import com.revature.lmp1.dtos.requests.NewUserRequest;
import com.revature.lmp1.dtos.responses.Principal;
import com.revature.lmp1.models.Reimbursement;
import com.revature.lmp1.models.User;
import com.revature.lmp1.services.ReimbService;
import com.revature.lmp1.services.TokenService;
import com.revature.lmp1.services.UserService;
import com.revature.lmp1.utils.custom_exceptions.InvalidRequestException;
import com.revature.lmp1.utils.custom_exceptions.InvalidSQLException;
import com.revature.lmp1.utils.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class EmployeeServlet extends HttpServlet {

    private final ObjectMapper mapper;

    private final TokenService tokenService;
    private final UserService userService;
    private final ReimbService reimbService;
    public EmployeeServlet(ObjectMapper mapper, TokenService tokenService, UserService userService, ReimbService reimbService) {
        this.mapper = mapper;
        this.tokenService = tokenService;
        this.userService = userService;
        this.reimbService = reimbService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String token = req.getHeader("Authorization");
            Principal principal = tokenService.extractRequesterDetails(token);

            String[] path = req.getRequestURI().split("/");
            if (principal.getRole().equals("3")) {
                if (path[3].equals("new_reimbursement")) {
                    NewReimbRequest request = mapper.readValue(req.getInputStream(), NewReimbRequest.class);
                    Reimbursement reimb = reimbService.newReimbursement(request,principal.getId());
                    System.out.println("amount: " + reimb.getAmount());
                    System.out.println(reimb.getTypeId());
                    System.out.println(reimb.getDescription());


                    resp.setContentType("application/json");
                    resp.getWriter().write(mapper.writeValueAsString(reimb));

                } else {
                    System.out.println("Error");
                }
            }
            else{
                resp.setStatus(403); //Forbidden
            }


        }catch (InvalidSQLException e){
            resp.setStatus(404);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch (InvalidRequestException e) {
            resp.setStatus(404);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch (ResourceConflictException e) {
            resp.setStatus(409);
        } catch (Exception e) {
            resp.setStatus(404); // BAD REQUEST
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



    }
}