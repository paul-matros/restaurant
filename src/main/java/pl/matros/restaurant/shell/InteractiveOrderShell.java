package pl.matros.restaurant.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import pl.matros.restaurant.model.OrderItem;
import pl.matros.restaurant.model.PlacedOrder;
import pl.matros.restaurant.service.InteractiveWaiter;
import pl.matros.restaurant.service.OrderService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class InteractiveOrderShell {

    private final OrderService orderService;
    private final InteractiveWaiter interactiveWaiter;

    @ShellMethod(key = "order", value = "Place a new order")
    public String order() {
        List<OrderItem> orderItems = interactiveWaiter.takeOrder();
        if (orderItems.isEmpty()) {
            return "Order cancelled - no items selected.";
        }
        PlacedOrder savedOrder = orderService.placeOrder(orderItems);
        System.out.println(orderService.getOrderSummary(orderItems));
        return String.format("Order placed successfully! Order ID: %d", savedOrder.getId());
    }

}