package servlets;

import com.google.gson.Gson;
import entities.PayOut;
import entities.Product;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "payOutServlet", value = { "/payout-post", "/payout-get", "/payout-delete" })
public class PayOutServlet extends HttpServlet {

    SessionFactory sf = HibernateUtil.getSessionFactory();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pid = 0;
        Session sesi = sf.openSession();
        Transaction tr = sesi.beginTransaction();

        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate= DateFor.format(date);

        try{
            String obj = req.getParameter("obj");
            Gson gson = new Gson();
            PayOut payOut = gson.fromJson(obj, PayOut.class);
            payOut.setDate(stringDate);
            sesi.saveOrUpdate(payOut);
            tr.commit();
            sesi.close();
            pid = 1;

        }catch (Exception ex){
            System.err.println("Save OR Update Error : " + ex);

        }finally {
            sesi.close();

        }
        resp.setContentType("application/json");
        resp.getWriter().write( "" +pid );


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Session sesi = sf.openSession();
        List<PayOut> ls = sesi.createQuery("from PayOut ").getResultList();
        sesi.close();


        String stJson = gson.toJson(ls);
        resp.setContentType("application/json");
        resp.getWriter().write( stJson );
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int return_id = 0;
        Session sesi = sf.openSession();
        Transaction tr = sesi.beginTransaction();
        try {
            int payout_id = Integer.parseInt( req.getParameter("payout_id") );
            PayOut payOut = sesi.load(PayOut.class, payout_id);
            sesi.delete(payOut);
            tr.commit();
            return_id = payOut.getPayout_id();
        }catch (Exception ex) {
            System.err.println("Delete Error : " + ex);
        }finally {
            sesi.close();
        }

        resp.setContentType("application/json");
        resp.getWriter().write( ""+return_id );
    }

}

