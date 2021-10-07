package servlets;

import utils.DBUtil;
import utils.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "loginServlet", value = {"/login-servlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        System.out.println("email = " + email + " password = " + password + " remember = " + remember);

        DBUtil util = new DBUtil();
        int status = util.login(email, password, remember, req, resp);
        System.out.println("status = " + status);
        if (status != 0) {
            resp.sendRedirect(Util.base_url + "dashboard.jsp");
        } else {
            req.setAttribute("loginError", "Mail veya Şifre Hatalı!");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
        }
    }


}
