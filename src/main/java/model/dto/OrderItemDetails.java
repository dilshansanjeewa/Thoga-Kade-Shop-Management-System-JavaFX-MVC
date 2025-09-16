package model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class OrderItemDetails {
    private String ItemCode;
    private String description;
    private String packSize;
    private double unitPrice;
    private int discount;
    private int qty;
    private double totalDiscount;
    private double netTotal;
}
