package model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class OrderDetail {
    private String orderId;
    private String itemCode;
    private int orderQty;
    private int discount;
}
