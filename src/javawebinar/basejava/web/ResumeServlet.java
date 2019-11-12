package javawebinar.basejava.web;

import sun.misc.CharacterEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        String param = request.getParameter("name");
        String prefix = request.getParameter("prefix");
        String answer = param != null ? "hello " + (prefix != null ? prefix : "") + param + "!" : "hello, friend!";
        response.getWriter().println(answer);
    }
}
