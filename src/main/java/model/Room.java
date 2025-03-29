package model;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private Integer id;
    private String type;
    private Double price;
    private String status;
}
