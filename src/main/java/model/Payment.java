package model;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Integer id;
    private Integer reservationNo;
    private LocalDate payDate;
    private Double totalDue;
    private Double discount;
    private Double tax;
}
