package com.revature.lmp1.services;

import com.revature.lmp1.daos.ReimbDAO;
import com.revature.lmp1.daos.UserDAO;
import com.revature.lmp1.dtos.requests.ReimbHistoryRequest;
import com.revature.lmp1.services.ReimbService;

import com.revature.lmp1.utils.custom_exceptions.InvalidRequestException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ReimbServiceTest {

    private ReimbService sut;

    private final ReimbDAO mockReimbDAO = mock(ReimbDAO.class);
    private final UserDAO mockUserDAO = mock(UserDAO.class);

    @Before
    public void setup() {sut = new ReimbService(mockReimbDAO, mockUserDAO);}

    @Test
    public void test_IsValidStatus_GivenValid(){
        ReimbService spiedSut = Mockito.spy(sut);
        String validStatus = "pending";
        List<String> statuses = new ArrayList<>();
        statuses.add("pending");
        statuses.add("approved");
        statuses.add("denied");

        when(mockReimbDAO.getStatuses()).thenReturn(statuses);

        when(spiedSut.isValidStatus(validStatus)).thenReturn(true);
        boolean flag = sut.isValidStatus(validStatus);
        Assert.assertTrue(flag);

        sut.isValidStatus(validStatus);
    }


    @Test
    public void test_getHistory_givenValid(){
        ReimbHistoryRequest request = new ReimbHistoryRequest("pending","submitted","ASC");
        String id = "valid";
        List<String> statuses = new ArrayList<>();
        statuses.add("pending");
        statuses.add("approved");
        statuses.add("denied");

        when(mockReimbDAO.getStatuses()).thenReturn(statuses);
        sut.getHistory(request,id);

    }



    @Test(expected = InvalidRequestException.class)
    public void test_IsValidStatus_GivenNothing() {
        String invalidStatus = "";

        sut.isValidStatus(invalidStatus);
    }



}
