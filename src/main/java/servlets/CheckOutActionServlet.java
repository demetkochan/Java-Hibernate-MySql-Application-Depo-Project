package servlets;

import com.google.gson.Gson;
import entities.BoxCustomerProduct;
import entities.Customer;
import entities.PayIn;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "checkOutActionServlet", value = "/checkPayOutAction-get")
public class CheckOutActionServlet extends HttpServlet {

    SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Session sesi = sf.openSession();


        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        int cid = Integer.parseInt(req.getParameter("cid"));
        int selectedType = Integer.parseInt(req.getParameter("selectedType"));

        if(selectedType ==2){
          String  sql = "select * from Payout where Payout.date BETWEEN "+ " '"+startDate.toString()+"' " + " and " + "'"+endDate.toString()+"'"  ;
            List<PayOut> ls = sesi.createNativeQuery(sql)
                    .addEntity(PayOut.class)
                    .getResultList();

            sesi.close();

            String stJson = gson.toJson(ls);
            resp.setContentType("application/json");
            resp.getWriter().write( stJson );

        }else{
           String sql = "select Customer.*, Payin.* from Payin INNER JOIN Customer on Payin.pay_customer_id = Customer.cu_id where Payin.pay_customer_id = ?1 and Payin.date BETWEEN " + " '"+startDate.toString()+"' " + " and " + "'"+endDate.toString()+"'"   ;
            System.out.println(sql);
           List<PayIn> ls = sesi.createNativeQuery(sql)
                    .setParameter(1,cid)
                    .addEntity("payin",PayIn.class)
                    .addEntity("customer", Customer.class)
                    .getResultList();

            sesi.close();

            String stJson = gson.toJson(ls);
            resp.setContentType("application/json");
            resp.getWriter().write( stJson );




        }




    }
}
