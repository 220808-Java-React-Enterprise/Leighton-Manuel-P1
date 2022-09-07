package com.revature.lmp1.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lmp1.dtos.requests.LoginRequest;
import com.revature.lmp1.dtos.requests.NewReimbRequest;
import com.revature.lmp1.dtos.requests.NewUserRequest;
import com.revature.lmp1.dtos.responses.Principal;
import com.revature.lmp1.models.User;
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

public class UserServlet extends HttpServlet {

    private final ObjectMapper mapper;

    private final TokenService tokenService;
    private final UserService userService;

    public UserServlet(ObjectMapper mapper, TokenService tokenService, UserService userService) {
        this.mapper = mapper;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String[] path = req.getRequestURI().split("/");

            if (path[3].equals("signup")) {
                NewUserRequest request = mapper.readValue(req.getInputStream(), NewUserRequest.class);
                User createdUser = userService.register(request);

                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString(createdUser));
            } else if (path[3].equals("new_reimbursement")) {
                NewReimbRequest request2 = mapper.readValue(req.getInputStream(), NewReimbRequest.class);



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


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //String token = req.getHeader("Authorization");
        //Principal principal = tokenService.extractRequesterDetails(token);

        try {
            //1 is equivalent to admin
                String username = req.getParameter("username");

                resp.setContentType("application/json");
                if (username != null) {
                    resp.getWriter().write(mapper.writeValueAsString(userService.getUserByUsername(username)));
                } else {
                    List<User> userList = userService.getAllUsers();
                    System.out.println(userList.size());
                    resp.getWriter().write(mapper.writeValueAsString(userList));
                }

        } catch (NullPointerException e) {
            resp.setStatus(401); // UNAUTHORIZED
        } catch (InvalidRequestException e) {
            resp.setStatus(404);
        } catch (Exception e){
            resp.getWriter().write("Something unexpected went wrong!");
            resp.setStatus(404);
        }
    }
}