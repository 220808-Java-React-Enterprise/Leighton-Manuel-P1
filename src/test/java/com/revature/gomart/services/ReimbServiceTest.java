package com.revature.gomart.services;

import com.revature.lmp1.daos.ReimbDAO;
import com.revature.lmp1.daos.UserDAO;
import com.revature.lmp1.dtos.requests.ReimbHistoryRequest;
import com.revature.lmp1.services.ReimbService;

import com.revature.lmp1.utils.custom_exceptions.InvalidRequestException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ReimbServiceTest {

    private ReimbService sut;

    private final ReimbDAO mockReimbDAO = mock(ReimbDAO.class);
    private final UserDAO mockUserDAO = mock(UserDAO.class);

    @Before
    public void setup() {sut = new ReimbService(mockReimbDAO, mockUserDAO);}
    /*

    @Test
    public void test_IsValidStatus_GivenValid(){
        //Arrange

        ReimbService spiedSut = Mockito.spy(sut);
        String validStatus = "pending";
        when(spiedSut.isValidStatus(validStatus)).thenReturn(true);
        boolean flag = sut.isValidStatus(validStatus);
        Assert.assertTrue(flag);


        when(spiedSut.isValidPassword(validPassword)).thenReturn(true);
        when(mockUserDao.getUserByUsernameAndPassword(validUsername, validPassword)).thenReturn(new User());

        // Act
        User user = spiedSut.login(validUsername, validPassword);

        // Assert
        Assert.assertNotNull(user);
        verify(mockUserDao, times(1)).getUserByUsernameAndPassword(validUsername, validPassword);


    }


    @Test
    public void test_getHistory_givenValid(){
        ReimbHistoryRequest request = new ReimbHistoryRequest("pending","submitted","ASC");
        String id = "valid";
        sut.getHistory(request,id);

    }


     */
    @Test(expected = InvalidRequestException.class)
    public void test_IsValidStatus_GivenNothing() {
        String invalidStatus = "";

        sut.isValidStatus(invalidStatus);
    }



}
