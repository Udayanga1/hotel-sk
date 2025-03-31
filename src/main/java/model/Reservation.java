package model;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private Integer reservationId;
    private Integer customerId;
    private Integer roomNo;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
