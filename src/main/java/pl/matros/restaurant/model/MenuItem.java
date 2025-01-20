package pl.matros.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MenuItem implements MenuDisplayable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;

    @Enumerated(EnumType.STRING)
    private MenuItemType type;

    @ManyToOne
    private Cuisine cuisine;

    @Override
    public String getDisplayName() {
        return this.name;
    }
}