package pl.matros.restaurant.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matros.restaurant.model.*;
import pl.matros.restaurant.repository.CuisineRepository;
import pl.matros.restaurant.repository.MenuItemRepository;
import pl.matros.restaurant.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CuisineRepository cuisineRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;

    public List<Cuisine> getAllCuisines() {
        return cuisineRepository.findAll();
    }

    public List<MenuItem> getMenuItemsByCuisineAndType(Cuisine cuisine, MenuItemType menuItemType) {
        return menuItemRepository.findByCuisineAndType(cuisine, menuItemType);
    }

    public List<MenuItem> getMenuItemsByType(MenuItemType menuItemType) {
        return menuItemRepository.findByType(menuItemType);
    }


    @Transactional
    public PlacedOrder placeOrder(List<OrderItem> items) {
        PlacedOrder order = new PlacedOrder();
        order.setItems(items.toString());
        order.setTotalPrice(calculateTotalOrderPrice(items));
        return orderRepository.save(order);
    }

    private int calculateTotalOrderPrice(List<OrderItem> items) {
        return items.stream()
                .mapToInt(this::calculateOrderItemPrice)
                .sum();
    }

    private int calculateOrderItemPrice(OrderItem item) {
        int itemPrice = item.getMenuItem().getPrice();
        int additionsPrice = item.getAdditions().stream()
                .mapToInt(MenuItem::getPrice)
                .sum();
        return itemPrice + additionsPrice;
    }

    public String getOrderSummary(List<OrderItem> items) {
        StringBuilder summary = new StringBuilder("Order Summary:\n");

        for (OrderItem item : items) {
            int itemTotal = calculateOrderItemPrice(item);
            summary.append(String.format("- %s: %s\n",
                    item.getMenuItem().getName(),
                    formatPrice(item.getMenuItem().getPrice())));

            for (MenuItem addition : item.getAdditions()) {
                summary.append(String.format("  + %s: %s\n",
                        addition.getName(),
                        formatPrice(addition.getPrice())));
            }
        }

        summary.append(String.format("Total: %s",
                formatPrice(calculateTotalOrderPrice(items))));

        return summary.toString();
    }

    public String formatPrice(int price) {
        return String.format("$%.2f", price / 100.0);
    }
}
