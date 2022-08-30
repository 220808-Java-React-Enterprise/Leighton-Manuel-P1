package com.revature.lmp1.services;

import com.revature.lmp1.daos.UserDAO;
import com.revature.lmp1.dtos.requests.LoginRequest;
import com.revature.lmp1.dtos.requests.NewUserRequest;
import com.revature.lmp1.models.User;
import com.revature.lmp1.utils.custom_exceptions.InvalidRequestException;
import com.revature.lmp1.utils.custom_exceptions.InvalidUserException;
import com.revature.lmp1.utils.custom_exceptions.ResourceConflictException;

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
                                        request.getPassword1(),
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

    public User login(LoginRequest request) {
        User user = userDAO.getByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (user == null) throw new InvalidRequestException("User not found");
        return user;
    }

    public void deleteUser(String id) {
        userDAO.delete(id);
    }

    public User getById(String id) {
        User user = userDAO.getById(id);
        if (user == null) throw new InvalidRequestException("User not found");
        return user;
    }

    public void deactivateUser(String id) {
        userDAO.setActive(id, false);
    }

    public void activateUser(String id) {
        userDAO.setActive(id, true);
    }

    public void resetUserPassword(String id, String password) {
        userDAO.resetPassword(id, password);
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
}
