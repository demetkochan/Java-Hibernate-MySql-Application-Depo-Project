package entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class BoxCustomerProduct {
    @Id
    private int box_id;
    //private int pay_id;
    private String cu_name;
    private String cu_surname;
    private String pro_title;
    private int pro_sale_price;
    private int box_receipt;
    private int box_count;

}
