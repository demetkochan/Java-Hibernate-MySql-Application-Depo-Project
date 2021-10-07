package servlets;

import com.google.gson.Gson;
import entities.BoxCustomerProduct;
import entities.Customer;
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

@WebServlet(name = "customerSearchServlet", value = "/customer-search")
public class CustomerSearchServlet extends HttpServlet {

    SessionFactory sf = HibernateUtil.getSessionFactory();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Gson gson = new Gson();
        Session sesi = sf.openSession();
        List<Customer> ls = sesi.createNativeQuery("SELECT * from customer where customer.cu_name like '%" + name + "%'")
                .addEntity(Customer.class)
                .getResultList();
        sesi.close();

        String stJson = gson.toJson(ls);
        resp.setContentType("application/json");
        resp.getWriter().write( stJson );
    }
}
