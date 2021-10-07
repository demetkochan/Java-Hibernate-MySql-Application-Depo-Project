package entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pro_id;

    private String pro_title;
    private int pro_purchase_price;
    private int pro_sale_price;
    private long pro_code;
    private int pro_KDV;
    private int pro_unit;
    private int pro_amount;
    private String pro_detail;


}
