package com.revature.lmp1.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lmp1.dtos.requests.*;
import com.revature.lmp1.models.User;
import com.revature.lmp1.services.UserService;
import com.revature.lmp1.utils.custom_exceptions.InvalidRequestException;
import com.revature.lmp1.utils.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminServlet extends HttpServlet {
    private final ObjectMapper mapper;

    private final UserService userService;

    public AdminServlet(ObjectMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //UserRequest request = mapper.readValue(req.getInputStream(), UserRequest.class);
            User calledUser;
            String[] path = req.getRequestURI().split("/");


            switch (path[3]) {
                case "activate":
                    UserRequest request = mapper.readValue(req.getInputStream(), UserRequest.class);
                    userService.activateUser(request);
                    calledUser = userService.getById(request.getId());

                    resp.setStatus(200);
                    resp.setContentType("application/json");
                    resp.getWriter().write(mapper.writeValueAsString(calledUser));
                    break;
                case "deactivate":
                    request = mapper.readValue(req.getInputStream(), UserRequest.class);
                    userService.deactivateUser(request);
                    calledUser = userService.getById(request.getId());

                    resp.setStatus(200);
                    resp.setContentType("application/json");
                    resp.getWriter().write(mapper.writeValueAsString(calledUser));
                    break;
                case "change_password":
                    PasswordResetRequest passRequest = mapper.readValue(req.getInputStream(), PasswordResetRequest.class);
                    userService.resetUserPassword(passRequest);
                    calledUser = userService.getById(passRequest.getId());

                    resp.setStatus(200);
                    resp.setContentType("application/json");
                    resp.getWriter().write(mapper.writeValueAsString(calledUser));
                    break;
                default:
                    System.out.println("Error");
                    break;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            NewUserRequest request = mapper.readValue(req.getInputStream(), NewUserRequest.class);

            String[] path = req.getRequestURI().split("/");

            if (path[3].equals("signup")) {
                User createdUser = userService.register(request);

                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString(createdUser));
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
