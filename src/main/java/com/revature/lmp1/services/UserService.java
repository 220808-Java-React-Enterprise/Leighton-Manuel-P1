package com.revature.lmp1.services;

import com.revature.lmp1.daos.UserDAO;
import com.revature.lmp1.models.User;
import com.revature.lmp1.utils.custom_exceptions.InvalidUserException;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void register(User user) {
        userDAO.save(user);
    }

    public User login(String username, String password) {
        User user = userDAO.getByUsernameAndPassword(username, password);
        if (user == null) throw new InvalidUserException("User not found");
        return user;
    }

    public void deleteUser(String id) {
        userDAO.delete(id);
    }

    public User getById(String id) {
        User user = userDAO.getById(id);
        if (user == null) throw new InvalidUserException("User not found");
        return user;
    }

    public void deactivateUser(String id) {
        userDAO.setActive(id, false);
    }

    public void activateUser(String id) {
        userDAO.setActive(id, true);
    }

    public void resetUserPassword (String id, String password) {
        userDAO.resetPassword(id, password);
    }
}
