package model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Setter@Getter
@ToString
public class Order {
    private String orderId;
    private LocalDate today;
    private String custId;
    private ArrayList<OrderDetail> orderDetailArrayList;
}
