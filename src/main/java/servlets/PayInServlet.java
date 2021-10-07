package servlets;

import com.google.gson.Gson;
import entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "payInServlet", value = {"/payIn-Servlet", "/payInPost-Servlet","/payin-delete" })
public class PayInServlet extends HttpServlet {
    SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();
        Session sesi = sf.openSession();

        int cid = Integer.parseInt(req.getParameter("cid"));

        List<BoxCustomerProduct> ls = sesi.createNativeQuery("SELECT * FROM boxaction as box\n" +
                "INNER JOIN customer as cu\n" +
                "on box.box_customer_id = cu.cu_id\n" +
                "INNER JOIN product as pro\n" +
                "on pro.pro_id = box.box_product_id\n" +
                "WHERE box.box_customer_id = ?1")
                .setParameter(1,cid)
                .addEntity(BoxCustomerProduct.class)
                .getResultList();
        /*
        List<PayIn> ls = sesi.createNativeQuery("select payin.*, customer.* from payin inner join customer on payin.pay_customer_id = customer.cu_id")
                .addEntity("payin",PayIn.class)
                .addEntity("customer", Customer.class)
                .getResultList();


         */
        sesi.close();

        String stJson = gson.toJson(ls);
        resp.setContentType("application/json");
        resp.getWriter().write( stJson );
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int bid=0;
        Session sesi = sf.openSession();
        Transaction tr = sesi.beginTransaction();

        try{
            String obj = req.getParameter("obj");
            Gson gson = new Gson();
            PayIn payIn = gson.fromJson(obj,PayIn.class);
            sesi.save(payIn);
            tr.commit();
            sesi.close();
            bid=1;

        }catch (Exception ex){
            System.err.println("Save Error :" + ex );

        }finally {
            sesi.close();
        }

        resp.setContentType("application/json");
        resp.getWriter().write( "" +bid );
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int return_id = 0;
        Session sesi = sf.openSession();
        Transaction tr = sesi.beginTransaction();
        try {
            int pay_id = Integer.parseInt( req.getParameter("pay_id") );
            PayIn payIn = sesi.load(PayIn.class, pay_id);
            sesi.delete(payIn);
            tr.commit();
            return_id = payIn.getPay_id();
        }catch (Exception ex) {
            System.err.println("Delete Error : " + ex);
        }finally {
            sesi.close();
        }

        resp.setContentType("application/json");
        resp.getWriter().write( ""+return_id );
    }
}
