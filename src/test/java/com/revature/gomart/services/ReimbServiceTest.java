package com.revature.gomart.services;

import com.revature.lmp1.daos.ReimbDAO;
import com.revature.lmp1.daos.UserDAO;
import com.revature.lmp1.services.ReimbService;

import com.revature.lmp1.utils.custom_exceptions.InvalidRequestException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReimbServiceTest {

    private ReimbService sut;

    private final ReimbDAO mockReimbDAO = mock(ReimbDAO.class);
    private final UserDAO mockUserDAO = mock(UserDAO.class);

    @Before
    public void setup() {sut = new ReimbService(mockReimbDAO, mockUserDAO);}
}
