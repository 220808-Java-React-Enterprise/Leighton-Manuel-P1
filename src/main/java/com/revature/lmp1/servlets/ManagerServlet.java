package com.revature.lmp1.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lmp1.dtos.requests.NewUserRequest;
import com.revature.lmp1.dtos.requests.PasswordResetRequest;
import com.revature.lmp1.dtos.requests.UserRequest;
import com.revature.lmp1.models.Reimbursement;
import com.revature.lmp1.models.User;
import com.revature.lmp1.services.ReimbService;
import com.revature.lmp1.services.UserService;
import com.revature.lmp1.utils.custom_exceptions.InvalidRequestException;
import com.revature.lmp1.utils.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class ManagerServlet extends HttpServlet {
    private final ObjectMapper mapper;

    private final UserService userService;
    private final ReimbService reimbService;

    public ManagerServlet(ObjectMapper mapper, UserService userService, ReimbService reimbService) {
        this.mapper = mapper;
        this.userService = userService;
        this.reimbService = reimbService;
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //NewUserRequest request = mapper.readValue(req.getInputStream(), NewUserRequest.class);

            String[] path = req.getRequestURI().split("/");

            if (path[3].equals("pending_reimbursements")) {
                Object ArrayList;
                List<Reimbursement> pending = reimbService.getPending();


                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString(pending));
            } else {
                System.out.println("Error");
            }


        } catch (InvalidRequestException e) {
            resp.setStatus(404);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch (ResourceConflictException e) {
            resp.setStatus(409);
        } catch (Exception e) {
            resp.setStatus(404); // BAD REQUEST
        }
    }
    }




