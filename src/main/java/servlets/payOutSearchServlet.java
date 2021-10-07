package servlets;

import com.google.gson.Gson;
import entities.Customer;
import entities.PayOut;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "payOutSearchServlet", value = "/payout-search")
public class payOutSearchServlet extends HttpServlet {

    SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        Gson gson = new Gson();
        Session sesi = sf.openSession();
        List<PayOut> ls = sesi.createNativeQuery("SELECT * from payout where payout.payout_title like '%" + title + "%'")
                .addEntity(PayOut.class)
                .getResultList();
        sesi.close();

        String stJson = gson.toJson(ls);
        resp.setContentType("application/json");
        resp.getWriter().write( stJson );
    }
}
