package utils;

import entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DBUtil {

    SessionFactory sf = HibernateUtil.getSessionFactory();

    public int login(String email, String password, String remember, HttpServletRequest req, HttpServletResponse resp) {

        Session sesi = sf.openSession();
        List<Admin> ls = null;
        try {
            String sql = "from Admin where email=?1 and password=?2";
            ls = sesi
                    .createQuery(sql)
                    .setParameter(1, email)
                    .setParameter(2,Util.MD5(password))
                    .getResultList();
            System.out.println("status : " + ls.size());
            // COOKIE
            if(ls.size() !=0){
                int id = ls.get(0).getId();
                String name = ls.get(0).getName();

                req.getSession().setAttribute("id", id);
                req.getSession().setAttribute("name", name);

                if ( remember != null && remember.equals("on")) {
                    name = name.replaceAll(" ", "_");
                    String val = id+"_"+name;
                    Cookie cookie = new Cookie("admin", val);
                    cookie.setMaxAge( 60*60 );
                    resp.addCookie(cookie);
                }
            }
        } catch (Exception e) {
            System.err.println("Login Error : " + e);
        } finally {
            sesi.close();
        }

        return ls.size();
    }

    //All Customer
    public List<Customer> customerList(){
        Session sesi = sf.openSession();
        List<Customer> ls = sesi.createQuery("from Customer ").getResultList();
        return ls;

    }



    //total payın
    public Long payInTotal(){
        Session sesi = sf.openSession();
        String sql = "select SUM(pay_price) from PayIn ";
        Long total = (Long) sesi.createQuery(sql).getSingleResult();
        System.out.println(total);

        return total;
    }

    //total payout
    public Long payOutTotal(){
        Session sesi = sf.openSession();
        String sql = "select SUM(payout_price) from PayOut ";
        Long total = (Long) sesi.createQuery(sql).getSingleResult();
        System.out.println(total);

        return total;
    }

    //total payInToday
    public Long payInTodayTotal(){
        Session sesi = sf.openSession();
        String sql = "select SUM(pay_price) from PayIn where date = CURDATE() ";
        Long total = (Long) sesi.createQuery(sql).getSingleResult();
        System.out.println(total);

        return total;
    }
    //total payOutToday

    public Long payOutTodayTotal(){
        Session sesi = sf.openSession();
        String sql = "select SUM(payout_price) from PayOut where date = CURDATE()";
        Long total = (Long) sesi.createQuery(sql).getSingleResult();
        System.out.println(total);

        return total;
    }


    //total amount of product
    public Long totalCountProduct(){

        Session sesi = sf.openSession();
        String sql = "select SUM(pro_amount) from Product ";
        Long total = (Long) sesi.createQuery(sql).getSingleResult();
        System.out.println(total);

        return total;


    }

    //total purchase price

    public Long totalPurchasePrice(){

        Session sesi = sf.openSession();
        String sql = "select SUM(pro_purchase_price) from Product ";
        Long total = (Long) sesi.createQuery(sql).getSingleResult();
        System.out.println(total);

        return total;


    }

    //total sale_price
    public Long totalSalePrice(){

        Session sesi = sf.openSession();
        String sql = "select SUM(pro_sale_price) from Product ";
        Long total = (Long) sesi.createQuery(sql).getSingleResult();
        System.out.println(total);

        return total;


    }






    //All Product
    public List<Product> ProductList(){
        Session sesi = sf.openSession();
        List<Product> ls = sesi.createQuery("from Product ").getResultList();
        return ls;

    }



    //son 5 stok ürün listesi
    public List<Product> products(){
        Session sesi = sf.openSession();
        List<Product> ls =  sesi.createNativeQuery("SELECT * FROM product \n" +
                "ORDER BY product.pro_id DESC\n" +
                "LIMIT 5 \n")
                .addEntity(Product.class)
                .getResultList();


        return ls;


    }

    public List<BoxCustomerProduct> boxCustomerProductList(){

        Session sesi = sf.openSession();
        List<BoxCustomerProduct> ls =  sesi.createNativeQuery("SELECT * FROM boxaction \n" +
                "INNER JOIN customer \n" +
                "ON boxaction.box_customer_id = customer.cu_id\n" +
                "INNER JOIN product\n" +
                "ON product.pro_id = boxaction.box_product_id\n" +
                "ORDER BY boxaction.box_receipt DESC\n" +
                "LIMIT 5 ")
                .addEntity(BoxCustomerProduct.class)
                .getResultList();

        return ls;


    }


    //list all order
    public List<BoxAction> boxActions(){
        Session sesi = sf.openSession();
        List<BoxAction> ls = sesi.createQuery("from BoxAction ").getResultList();

        return ls;
    }





    //list all

    public String rememberPassword(String email){

        Session sesi = sf.openSession();

        String password = "";


        try{
            String sql = "  from Admin where email=?1";
           List<Admin> ls  = sesi.createQuery(sql)
                   .setParameter(1,email)
                   .getResultList();


           if(ls.size() !=0 ){
               password = ls.get(0).getPassword();
           }

        }catch (Exception ex){
            System.err.println("Remember Password Error" + ex);
        }finally {
            sesi.close();
        }
        return password;



    }



    public Admin isLogin(HttpServletRequest request, HttpServletResponse response){
        if (request.getCookies() != null){ //eğer cookimiz varsa işlemleri yapar
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie: cookies ){
                if (cookie.getName().equals("admin")){ //beni hatırla cookisi yapılmış mı
                    String values = cookie.getValue(); //yapının içindeki değeri getir

                    try{
                        String[] arr= values.split("_");
                        request.getSession().setAttribute("id",Integer.parseInt(arr[0]));
                        request.getSession().setAttribute("name",arr[1]+" " + arr[2]);

                    }catch (NumberFormatException e){ //cookie değiştirilmeye çalışılırsa cookie patlatılır
                        Cookie cookie1 = new Cookie("admin","");
                        cookie1.setMaxAge(0);
                        response.addCookie(cookie1);

                    }

                    break;

                }
            }
        }

        Object sessionObg = request.getSession().getAttribute("id");
        Admin adm = new Admin();
        if(sessionObg ==null){

            try{
                response.sendRedirect(Util.base_url);

            }catch (Exception ex){
                System.err.println("isLogin error: " + ex);

            }

        }else{
            //dashboard içinde girilen adminin ismi getirilir
            int aid = (int) request.getSession().getAttribute("id");
            String name = (String) request.getSession().getAttribute("name");
            adm.setId(aid);
            adm.setName(name);



        }
        return  adm;
    }








}

