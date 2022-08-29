package com.revature.lmp1.services;

import com.revature.lmp1.daos.UserDAO;

public class UserService {

    private final UserDAO userDAO;


    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }


}
