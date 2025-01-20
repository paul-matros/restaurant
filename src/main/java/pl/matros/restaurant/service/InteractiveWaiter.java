package pl.matros.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matros.restaurant.model.Cuisine;
import pl.matros.restaurant.model.MenuItem;
import pl.matros.restaurant.model.MenuItemType;
import pl.matros.restaurant.model.OrderItem;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InteractiveWaiter {
    private final OrderService orderService;
    private final InputHandler inputHandler;
    private final MenuSelector menuSelector;  // we'll create this too

    private Cuisine selectCuisine() {
        List<Cuisine> cuisines = orderService.getAllCuisines();
        return menuSelector.selectMenuItem(cuisines, "cuisine");
    }

    public OrderItem takeDrinkOrder() {
        MenuItem drink = menuSelector.selectMenuItem(
                orderService.getMenuItemsByType(MenuItemType.DRINK),
                "Select drink"
        );

        if (drink == null) {
            return null;
        }

        List<MenuItem> orderedAdditions = new LinkedList<>();
        if (inputHandler.getYesNoAnswer("Do you wish to add something to your drink?")) {
            orderedAdditions = selectAdditions(drink.getDisplayName(), MenuItemType.DRINK_ADDITION);
        }
        return new OrderItem(drink, orderedAdditions);
    }

    public List<OrderItem> takeLunchOrder() {
        List<OrderItem> lunch = new LinkedList<>();

        OrderItem mainCourse = takeMainCourseOrder();
        if (mainCourse != null) {
            lunch.add(mainCourse);
        }

        OrderItem dessert = takeDessertOrder();
        if (dessert != null) {
            lunch.add(dessert);
        }

        return lunch;
    }

    public OrderItem takeDessertOrder() {
        return new OrderItem(menuSelector.selectMenuItem(
                orderService.getMenuItemsByType(MenuItemType.DESSERT),
                "Select dessert"
        ));
    }

    public OrderItem takeMainCourseOrder() {
        Cuisine cuisine = selectCuisine();
        List<MenuItem> menuItems;
        String menuTitle;
        if (cuisine == null) {
            menuItems = orderService.getMenuItemsByType(MenuItemType.MAIN_COURSE);
            menuTitle = "Select meal";
        } else {
            menuItems = orderService.getMenuItemsByCuisineAndType(cuisine, MenuItemType.MAIN_COURSE);
            menuTitle = String.format("Select meal from %s cuisine", cuisine.getDisplayName());
        }

        MenuItem selectedItem = menuSelector.selectMenuItem(menuItems, menuTitle);
        return selectedItem != null
                ? new OrderItem(selectedItem)
                : null;
    }

    private List<MenuItem> selectAdditions(String menuItemName, MenuItemType additionType) {//todo create isAddition field in menuItem?
        List<MenuItem> orderedAdditions = new LinkedList<>();
        List<MenuItem> availableAdditions = new LinkedList<>(
                orderService.getMenuItemsByType(additionType)
        );

        while (!availableAdditions.isEmpty()) {
            MenuItem selectedAddition = menuSelector.selectMenuItem(
                    availableAdditions,
                    String.format("What would you like to add to your %s?", menuItemName)
            );
            if (selectedAddition == null) {
                break;
            }

            orderedAdditions.add(selectedAddition);
            availableAdditions.remove(selectedAddition);
        }
        return orderedAdditions;
    }

    public List<OrderItem> takeOrder() {
        List<OrderItem> orderItems = new LinkedList<>();

        if (inputHandler.getYesNoAnswer("Would you like to order lunch?")) {
            List<OrderItem> lunch = takeLunchOrder();
            orderItems.addAll(lunch);
        }

        if (inputHandler.getYesNoAnswer("Would you like to order a drink?")) {
            OrderItem drink = takeDrinkOrder();
            if (drink != null) {
                orderItems.add(drink);
            }
        }

        return orderItems;
    }
}