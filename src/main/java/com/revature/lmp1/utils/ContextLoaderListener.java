package com.revature.lmp1.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lmp1.daos.ReimbDAO;
import com.revature.lmp1.daos.UserDAO;
import com.revature.lmp1.services.ReimbService;
import com.revature.lmp1.services.TokenService;
import com.revature.lmp1.services.UserService;
import com.revature.lmp1.servlets.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ObjectMapper mapper = new ObjectMapper();

        TestServlet testServlet = new TestServlet(mapper);
        UserServlet userServlet = new UserServlet(mapper, new UserService(new UserDAO()));
        AuthServlet authServlet = new AuthServlet(mapper, new UserService(new UserDAO()), new TokenService(new JwtConfig()));
        AdminServlet adminServlet = new AdminServlet(mapper, new UserService(new UserDAO()));
        ManagerServlet managerServlet = new ManagerServlet(mapper, new UserService(new UserDAO()),new ReimbService(new ReimbDAO()));

        ServletContext context = sce.getServletContext();
        context.addServlet("TestServlet", testServlet).addMapping("/test");
        context.addServlet("UserServlet", userServlet).addMapping("/users/*");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");
        context.addServlet("AdminServlet", adminServlet).addMapping("/admin/*");
        context.addServlet("ManagerServlet",managerServlet).addMapping("/manager/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("\nShutting down");
    }
}
