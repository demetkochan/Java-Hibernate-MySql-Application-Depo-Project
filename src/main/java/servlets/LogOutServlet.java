package servlets;

import utils.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "loginOutServlet", value = {"/logout-servlet"})
public class LogOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("id");
        req.getSession().removeAttribute("name");
        req.getSession().invalidate();
        Cookie cookie = new Cookie("admin", "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
        resp.sendRedirect(Util.base_url + "index.jsp");
    }
}
