package pl.matros.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderItem { // todo record?
    private MenuItem menuItem;
    private List<MenuItem> additions;

    public OrderItem(MenuItem menuItem) {
        this.menuItem = menuItem;
        this.additions = new LinkedList<>();
    }
}