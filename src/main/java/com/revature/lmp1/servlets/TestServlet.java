package com.revature.lmp1.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestServlet extends HttpServlet {

    private final ObjectMapper mapper;
    public TestServlet(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String request = mapper.readValue(req.getInputStream(), String.class);

        resp.getWriter().write(hashPassword(request));
    }

    public String hashPassword(String pw) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pw.getBytes());

            byte[] b = md.digest();
            StringBuilder sb = new StringBuilder();

            for (byte i : b) {
                sb.append(Integer.toString((i & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
