package pl.matros.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PlacedOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String items;
    private int totalPrice;
}
