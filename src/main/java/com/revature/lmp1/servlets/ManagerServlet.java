package com.revature.lmp1.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lmp1.dtos.requests.NewUserRequest;
import com.revature.lmp1.dtos.requests.PasswordResetRequest;
import com.revature.lmp1.dtos.requests.ReimbStatusRequest;
import com.revature.lmp1.dtos.requests.UserRequest;
import com.revature.lmp1.dtos.responses.Principal;
import com.revature.lmp1.models.Reimbursement;
import com.revature.lmp1.models.User;
import com.revature.lmp1.services.ReimbService;
import com.revature.lmp1.services.TokenService;
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
    private final TokenService tokenService;

    public ManagerServlet(ObjectMapper mapper, UserService userService, ReimbService reimbService, TokenService tokenService) {
        this.mapper = mapper;
        this.userService = userService;
        this.reimbService = reimbService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        Principal principal = tokenService.extractRequesterDetails(token);
        if (principal.getRole().equals("2")) {
            try {
                String[] path = req.getRequestURI().split("/");
                if (path[3].equals("manage_pending")) {
                    ReimbStatusRequest request = mapper.readValue(req.getInputStream(), ReimbStatusRequest.class);
                    System.out.println(request.getStatus());
                    System.out.println(request.getId());
                    
                    //userService.activateUser(request);
                    reimbService.changeReimbStatus(request);

                    //calledUser = userService.getById(request.getId());

                    resp.setStatus(200);
                    resp.setContentType("application/json");
                    resp.getWriter().write("Reimbursement changed!");

                }
            }catch (InvalidRequestException e) {
                resp.setStatus(404);
                resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
            } catch (ResourceConflictException e) {
                resp.setStatus(409);
            } catch (Exception e) {
                resp.setStatus(404); // BAD REQUEST
            }
        }
        else{
            resp.setStatus(403);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        Principal principal = tokenService.extractRequesterDetails(token);
        if (principal.getRole().equals("2")) {
            try {
                String[] path = req.getRequestURI().split("/");
                if (path[3].equals("manage_pending")) {
                    ReimbStatusRequest request = mapper.readValue(req.getInputStream(), ReimbStatusRequest.class);
                    System.out.println(request.getStatus());
                    System.out.println(request.getId());


                    //userService.activateUser(request);
                    reimbService.changeReimbStatus(request);

                    //calledUser = userService.getById(request.getId());

                    resp.setStatus(200);
                    resp.setContentType("application/json");
                    resp.getWriter().write("Reimbursement changed!");

                }
            }catch (InvalidRequestException e) {
                resp.setStatus(404);
                resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
            } catch (ResourceConflictException e) {
                resp.setStatus(409);
            } catch (Exception e) {
                resp.setStatus(404); // BAD REQUEST
            }
        }
        else{
            resp.setStatus(403);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        Principal principal = tokenService.extractRequesterDetails(token);
        if (principal.getRole().equals("2")) {
            try {
                //NewUserRequest request = mapper.readValue(req.getInputStream(), NewUserRequest.class);

                String[] path = req.getRequestURI().split("/");

                if (path[3].equals("pending_reimbursements")) {
                    Object ArrayList;
                    List<Reimbursement> pending = reimbService.getPending();
                    resp.setStatus(200);
                    resp.setContentType("application/json");
                    for(Reimbursement i : pending){
                        //resp.getWriter().write(mapper.writeValueAsString(pending));
                        resp.getWriter().write(i.toString() + "\n");
                    }
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
        else{
            resp.setStatus(403); //forbidden
        }
    }
    }




