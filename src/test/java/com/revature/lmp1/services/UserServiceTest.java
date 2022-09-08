package com.revature.lmp1.services;

import com.revature.lmp1.daos.UserDAO;
import com.revature.lmp1.dtos.requests.LoginRequest;
import com.revature.lmp1.dtos.requests.NewUserRequest;
import com.revature.lmp1.dtos.requests.PasswordResetRequest;
import com.revature.lmp1.dtos.requests.UserRequest;
import com.revature.lmp1.dtos.responses.Principal;
import com.revature.lmp1.models.User;
import com.revature.lmp1.services.UserService;

import com.revature.lmp1.utils.custom_exceptions.AuthenticationException;
import com.revature.lmp1.utils.custom_exceptions.InvalidRequestException;
import com.revature.lmp1.utils.custom_exceptions.ResourceConflictException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class UserServiceTest {

    private UserService sut;

    private final UserDAO mockUserDao = mock(UserDAO.class);

    @Before
    public void setup() {sut = new UserService(mockUserDao);}

    // Entry is too long
    // Contains an illegal character (space)
    @Test(expected = InvalidRequestException.class)
    public void test_IsInvalidUsername_givenInvalidUsername() {
        String invalidUsername = "Smitty Werbenjagermanjensen";

        sut.isValidUsername(invalidUsername);
    }

    // username already exists in the database
    @Test(expected = ResourceConflictException.class)
    public void test_isDuplicateUsername_givenDuplicateUsername() {
        UserService spiedSut = Mockito.spy(sut);
        String validUsername = "username";
        String duplicateUsername = "username";

        when(spiedSut.isValidUsername(validUsername)).thenReturn(true);
        when(spiedSut.isValidUsername(duplicateUsername)).thenReturn(true);
        when(mockUserDao.getUsername(duplicateUsername)).thenReturn(validUsername);

        sut.isDuplicateUsername(duplicateUsername);
    }

    // Password does not meet regex criteria
    @Test(expected = InvalidRequestException.class)
    public void test_isInvalidPassword_givenInvalidPassword() {
        String invalidPassword = "password";

        sut.isValidPassword(invalidPassword);
    }

    // Passwords do not match
    @Test(expected = InvalidRequestException.class)
    public void test_isInvalidPassword_givenDifferentPasswords() {
        String password1 = "P@ssw0rd";
        String password2 = "P@sswOrd";

        sut.isSamePassword(password1, password2);
    }

    // Entry does not adhere to regex
    // Entry contains an illegal character
    @Test(expected = InvalidRequestException.class)
    public void test_isInvalidEmail_givenInvalidEmail() {
        String invalidEmail = "us%er.usersite@com";

        sut.isValidEmail(invalidEmail);
    }

    @Test(expected = ResourceConflictException.class)
    public void test_isInvalidEmail_givenDuplicateEmail() {
        UserService spiedSut = Mockito.spy(sut);
        String validEmail = "user1@website.com";
        String duplicateEmail = "user1@website.com";

        when(spiedSut.isValidEmail(validEmail)).thenReturn(true);
        when(spiedSut.isValidEmail(duplicateEmail)).thenReturn(true);
        when(mockUserDao.getEmail(duplicateEmail)).thenReturn(validEmail);

        sut.isDuplicateEmail(duplicateEmail);
    }

    @Test
    public void test_isValidRegistration_givenValidCredentials() {
        NewUserRequest request = new NewUserRequest(
                "User001",
                "P@ssw0rd",
                "P@ssw0rd",
                "user1@website.com",
                "New",
                "User"
        );
        sut.register(request);
    }

    @Test(expected = AuthenticationException.class)
    public void test_isInvalidLogin_givenIncorrectCredentials() {
        UserService spiedSut = Mockito.spy(sut);
        LoginRequest invalidReq = new LoginRequest("username1", "P@ssw0rd");

        when(spiedSut.isValidUsername(invalidReq.getUsername())).thenReturn(true);
        when(spiedSut.isValidPassword(invalidReq.getPassword())).thenReturn(true);
        when(mockUserDao.getByUsernameAndPassword(invalidReq.getUsername(), invalidReq.getPassword())).thenReturn(null);

        sut.login(invalidReq);
    }

    @Test(expected = AuthenticationException.class)
    public void test_isInvalidLogin_givenInactiveUser() {
        String username = "User001";
        String password = "P@ssw0rd";

        User inactiveUser = new User(
                "User001",
                "P@ssw0rd",
                "P@ssw0rd",
                "user1@website.com",
                "New",
                "User",
                false,
                null
        );

        when(mockUserDao.getByUsernameAndPassword(username, password)).thenReturn(inactiveUser);
        LoginRequest invalidReq = new LoginRequest(username, password);

        sut.login(invalidReq);
    }

    @Test(expected = InvalidRequestException.class)
    public void test_isInvalidRequest_givenInvalidId() {
        String invalidId = "00000";

        when(mockUserDao.getById(invalidId)).thenReturn(null);

        sut.getById(invalidId);
    }

    @Test(expected = InvalidRequestException.class)
    public void test_isInvalidActivateRequest_givenInvalidCredentials() {
        UserService spiedSut = Mockito.spy(sut);
        UserRequest invalidRequest = new UserRequest("0001", "Janitor");

        when(spiedSut.isValidRole(invalidRequest.getRole())).thenReturn(false);

        sut.activateUser(invalidRequest);
    }

    @Test(expected = InvalidRequestException.class)
    public void test_isInvalidDeactivateRequest_givenInvalidCredentials() {
        UserService spiedSut = Mockito.spy(sut);
        UserRequest invalidRequest = new UserRequest("0001", "Janitor");

        when(spiedSut.isValidRole(invalidRequest.getRole())).thenReturn(false);

        sut.deactivateUser(invalidRequest);
    }

    @Test
    public void test_isValidRole_givenValidRole() {
        UserService spiedSut = Mockito.spy(sut);
        String validRole = "employee";
        List<String> validRoles = new ArrayList<>();
        validRoles.add("employee");
        validRoles.add("admin");
        validRoles.add("manager");

        when(mockUserDao.getRoles()).thenReturn(validRoles);
        when(spiedSut.isValidRole(validRole)).thenReturn(true);

        sut.isValidRole(validRole);
    }

    @Test(expected = InvalidRequestException.class)
    public void test_isInvalidUsernameRequest_givenNonexistentUser() {
        UserService spiedSut = Mockito.spy(sut);
        String invalidUsername = "username";

        when(spiedSut.isValidUsername(invalidUsername)).thenReturn(true);
        when(mockUserDao.getUserByUsername(invalidUsername)).thenReturn(null);

        sut.getUserByUsername(invalidUsername);
    }

    @Test
    public void test_resetUserPassword_givenValidPasswords() {
        UserService spiedSut = Mockito.spy(sut);
        String validPassword1 = "P@ssw0rd";
        String validPassword2 = "P@ssw0rd";
        PasswordResetRequest validReq = new PasswordResetRequest("0001",validPassword1,validPassword2);


        when(spiedSut.isValidPassword(validPassword1)).thenReturn(true);
        when(spiedSut.isSamePassword(validPassword1, validPassword2)).thenReturn(true);
        when(mockUserDao.getById(validReq.getId())).thenReturn(new User());

        User user = spiedSut.getById(validReq.getId());

        Assert.assertNotNull(user);

        sut.resetUserPassword(validReq);
    }

    @Test
    public void test_hashPassword_givenValidPassword() {
        UserService spiedSut = Mockito.spy(sut);
        String validPassword = "P@ssw0rd";

        when(spiedSut.isValidPassword(validPassword)).thenReturn(true);

        sut.hashPassword(validPassword);
    }


}
