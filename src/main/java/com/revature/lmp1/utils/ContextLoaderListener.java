package com.revature.lmp1.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lmp1.daos.UserDAO;
import com.revature.lmp1.services.UserService;
import com.revature.lmp1.servlets.TestServlet;
import com.revature.lmp1.servlets.UserServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ObjectMapper mapper = new ObjectMapper();

        TestServlet testServlet = new TestServlet();
        UserServlet userServlet = new UserServlet(mapper, new UserService(new UserDAO()));

        ServletContext context = sce.getServletContext();
        context.addServlet("TestServlet", testServlet).addMapping("/test");
        context.addServlet("UserServlet", userServlet).addMapping("/users/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("\nShutting down");
    }
}
