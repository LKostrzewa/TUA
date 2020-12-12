package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/health")
public class HealthCheck extends HttpServlet {

    @Inject
    private HealthTest healthTest;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (healthTest.tua()) {
            resp.setStatus(200);
            resp.getWriter().write("Health Up");
        } else {
            resp.setStatus(503);
            resp.getWriter().write("Health Down");
        }

    }



}
