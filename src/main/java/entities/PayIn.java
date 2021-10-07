package entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class PayIn {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pay_id;
    private int pay_customer_id;
    private int pay_receipt_no;
    private int pay_price;
    private String pay_detail;
    private String date;




}
