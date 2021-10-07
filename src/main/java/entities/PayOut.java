package entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class PayOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int payout_id;
    private  String payout_title;
    private int payout_type;
    private int payout_price;
    private String payout_detail;

    private String date;




}
