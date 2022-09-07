package com.revature.lmp1.services;

import com.revature.lmp1.daos.UserDAO;
import com.revature.lmp1.dtos.requests.*;
import com.revature.lmp1.dtos.responses.Principal;
import com.revature.lmp1.models.Reimbursement;
import com.revature.lmp1.models.User;
import com.revature.lmp1.utils.custom_exceptions.AuthenticationException;
import com.revature.lmp1.utils.custom_exceptions.InvalidRequestException;
import com.revature.lmp1.utils.custom_exceptions.ResourceConflictException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class UserService {

    private final UserDAO userDAO;


    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User register(NewUserRequest request) {
        User user = null;

        if (isValidUsername(request.getUsername())) {
            if (!isDuplicateUsername(request.getUsername())) {
                if (isValidPassword(request.getPassword1())) {
                    if (isSamePassword(request.getPassword1(), request.getPassword2())) {
                        if (isValidEmail(request.getEmail())) {
                            if (!isDuplicateEmail(request.getEmail())) {
                                user = new User(
                                        UUID.randomUUID().toString(),
                                        request.getUsername(),
                                        request.getEmail(),
                                        hashPassword(request.getPassword1()),
                                        request.getGivenName(),
                                        request.getSurname()
                                );
                                userDAO.save(user);
                            }
                        }
                    }
                }
            }
        }

        return user;
    }

    public Principal login(LoginRequest request) {
        User user = userDAO.getByUsernameAndPassword(request.getUsername(), hashPassword(request.getPassword()));
        //System.out.println(hashPassword(request.getPassword()));
        //User user = userDAO.getByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (user == null) throw new AuthenticationException("User not found");
        if (!user.isActive()) throw new AuthenticationException("User is inactive");
        return new Principal(user.getId(), user.getUsername(), user.getRoleId());
    }

    public void deleteUser(String id) {
        userDAO.delete(id);
    }

    public User getById(String id) {
        User user = userDAO.getById(id);
        if (user == null) throw new InvalidRequestException("User not found");
        return user;
    }

    public void deactivateUser(UserRequest req) {
        if(isValidRole(req.getRole())) {
            userDAO.setActive(req.getId(), false, userDAO.getRoleId(req.getRole()));
        }

    }

    public void activateUser(UserRequest req) {
        if(isValidRole(req.getRole())) {
            userDAO.setActive(req.getId(), true, userDAO.getRoleId(req.getRole()));
        }
    }

    public boolean isValidRole(String role){
        role = role.toLowerCase().trim();
        System.out.println(role);
        if(!userDAO.getRoles().contains(role)){
            throw new InvalidRequestException("\nThe role of a new employee must be real! (Employee/Finance Manager/Admin");
        }
        return true;
    }

    public User getUserByUsername(String username) {
        if (userDAO.getUserByUsername(username) == null) throw new InvalidRequestException("\nInvalid request! There is no user by that username");
        return userDAO.getUserByUsername(username);
    }

    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

    public void resetUserPassword(PasswordResetRequest req) {
        //String password = generatePassword();
        if(isValidPassword(req.getPassword1())){
            if(isSamePassword(req.getPassword1(),req.getPassword2())){
                userDAO.resetPassword(req.getId(), req.getPassword1());
            }
        }

    }

    public boolean isValidUsername(String username) {
        if (!username.matches("^[a-zA-Z0-9_-]{3,15}$"))
            throw new InvalidRequestException("\nUsername must be between 3-15 characters and may only contain letters, numbers, dashes, and hyphens");
        return true;
    }


    public boolean isValidEmail(String email) {
        if (!email.matches("[A-Za-z0-9._-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"))
            throw new InvalidRequestException("\nPlease enter a valid email address");
        return true;
    }

    public boolean isValidPassword(String password) {
        if (!password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,20}$"))
            throw new InvalidRequestException("\nPassword must be 8-20 characters with at least one uppercase letter, one lowercase letter, one number, and one special character");
        return true;
    }

    public boolean isSamePassword(String password, String password2) {
        if (!password2.equals(password)) throw new InvalidRequestException("Passwords do not match!");
        return true;
    }

    public boolean isDuplicateUsername(String username) {
        if (userDAO.getUsername(username) != null) throw new ResourceConflictException("\nUsername not available!");
        return false;
    }

    public boolean isDuplicateEmail(String email) {
        if (userDAO.getEmail(email) != null) throw new ResourceConflictException("\nEmail not available!");
        return false;
    }

    public String hashPassword(String pw) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pw.getBytes());

            byte[] b = md.digest();
            StringBuilder sb = new StringBuilder();

            for (byte i : b) {
                sb.append(Integer.toString((i & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
