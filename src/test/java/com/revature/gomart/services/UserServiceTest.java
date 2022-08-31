package com.revature.gomart.services;

import com.revature.lmp1.daos.UserDAO;
import com.revature.lmp1.services.UserService;

import com.revature.lmp1.utils.custom_exceptions.InvalidRequestException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService sut;

    private final UserDAO mockUserDao = mock(UserDAO.class);

    @Before
    public void setup() {sut = new UserService(mockUserDao);}

}
