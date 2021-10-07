package servlets;

import com.google.gson.Gson;
import entities.BoxCustomerProduct;
import entities.PayIn;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(name = "paymentCompleteServlet", value = {"/paymentComplete-post", "/updatePayInDetail"})
public class PaymentCompleteServlet extends HttpServlet {

    SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cid = Integer.parseInt(req.getParameter("user_id"));

        AtomicInteger total = new AtomicInteger();
        AtomicInteger receiptNo = new AtomicInteger();

        Gson gson = new Gson();
        Session sesi = sf.openSession();
        Transaction tr = sesi.beginTransaction();
        List<BoxCustomerProduct> ls = sesi.createNativeQuery("SELECT * FROM boxaction as box\n" +
                "INNER JOIN customer as cu\n" +
                "on box.box_customer_id = cu.cu_id\n" +
                "INNER JOIN product as pro\n" +
                "on pro.pro_id = box.box_product_id\n" +
                "WHERE box.box_customer_id = ?1 and box.box_status=?2")
                .setParameter(1,cid)
                .setParameter(2,0)
                .addEntity(BoxCustomerProduct.class)
                .getResultList();
        ls.forEach(item->{

            total.addAndGet(item.getBox_count() * item.getPro_sale_price());
            receiptNo.addAndGet(item.getBox_receipt());

            String hqlUpdate = "update BoxAction c set c.box_status = :newStatus where c.box_receipt = :boxReceipt";

             sesi.createQuery( hqlUpdate )
                    .setParameter( "newStatus", 1 )
                    .setParameter( "boxReceipt", item.getBox_receipt() )
                    .executeUpdate();



        });
        int totalResult = total.intValue();
        int receiptNoResult = receiptNo.intValue();

        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate= DateFor.format(date);

        PayIn payIn = new PayIn();
        payIn.setPay_customer_id(cid);
        payIn.setPay_price(totalResult);
        payIn.setPay_receipt_no(receiptNoResult);
        payIn.setDate(stringDate);
        sesi.save(payIn);

        tr.commit();
        sesi.close();





        String stJson = gson.toJson(total);
        resp.setContentType("application/json");
        resp.getWriter().write( stJson );

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cid = Integer.parseInt(req.getParameter("user_id"));


        //SELECT * from payin where payin.pay_customer_id = 1 and payin.pay_receipt_no = 1
    }



}
