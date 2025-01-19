package pl.matros.restaurant.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import pl.matros.restaurant.model.*;
import pl.matros.restaurant.service.InputHandler;
import pl.matros.restaurant.service.OrderService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class InteractiveOrderShell {

    private final OrderService orderService;
    private final InputHandler inputHandler;

    @ShellMethod("Say hello")
    public String hello() {
        return "Hello, world!";
    }

    @ShellMethod(key = "order", value = "order lunch")
    public String order() {
        List<OrderItem> order = new LinkedList<>();
        if (inputHandler.getYesNoAnswer("Do you wish to order lunch?")) {
            System.out.println("Ordering lunch...");
            List<OrderItem> lunch = orderLunch();
            order.addAll(lunch);
        }
        if (inputHandler.getYesNoAnswer("Do you wish to order drink?")) {
            OrderItem drink = orderDrink();
            order.add(drink);
        }
        //orderService.placeOrder(order);
        return (order.toString());
    }

    private OrderItem orderDrink() {
        MenuItem drink = selectMenuItem(MenuItemType.DRINK);
        List<MenuItem> orderedAdditions = new LinkedList<>();
        if (inputHandler.getYesNoAnswer("Do you wish to add something to your drink?")) {
            List<MenuItem> drinkAdditions = orderService.getMenuItemsByType(MenuItemType.DRINK_ADDITION);
            MenuItem selectedAddition;
            do {
                selectedAddition = selectMenuItem(drinkAdditions, "What would you like to add to your drink?");
                orderedAdditions.add(selectedAddition);
                drinkAdditions.remove(selectedAddition);
            } while (selectedAddition != null && drinkAdditions.size() > 0);
        }
        return new OrderItem(drink, orderedAdditions);
    }

    private List<OrderItem> orderLunch() {
        List<OrderItem> lunch = new LinkedList<>();
        lunch.add(orderMainCourse());
        lunch.add(orderDessert());
        return lunch;
    }

    private OrderItem orderDessert() {
        return new OrderItem(selectMenuItem(MenuItemType.DESSERT));
    }

    private OrderItem orderMainCourse() {
        Cuisine cuisine = selectCuisine();
        OrderItem mainCourse;
        if (cuisine == null) {
            mainCourse = new OrderItem(selectMenuItem(MenuItemType.MEAL));
        } else {
            mainCourse = new OrderItem(selectMenuItem(MenuItemType.MEAL, cuisine));
        }
        return mainCourse;
    }

    private Cuisine selectCuisine() {
        List<Cuisine> cuisines = orderService.getAllCuisines();
        Optional<Displayable> cuisine = selectDisplayableItem(cuisines, "cuisine");
        return (Cuisine) cuisine.orElse(null);
    }

    private MenuItem selectMenuItem(MenuItemType menuItemType, Cuisine cuisine) {
        List<MenuItem> menuItems = orderService.getMenuItemsByCuisineAndType(cuisine, menuItemType);
        String menuTitle = String.format("Select %s menu item of %s cuisine", menuItemType.name(), cuisine.getDisplayName());
        return selectMenuItem(menuItems, menuTitle);
    }

    private MenuItem selectMenuItem(MenuItemType menuItemType) {
        List<MenuItem> menuItems = orderService.getMenuItemsByType(menuItemType);
        String menuTitle = String.format("Select %s menu item", menuItemType.name());
        return selectMenuItem(menuItems, menuTitle);
    }

    private MenuItem selectMenuItem(List<MenuItem> menuItems, String menuTitle) {
        Optional<Displayable> menuItem = selectDisplayableItem(menuItems, menuTitle);
        return (MenuItem) menuItem.orElse(null);
    }

    private Optional<Displayable> selectDisplayableItem(List<? extends Displayable> items, String menuTitle) {
        this.printDisplayableMenu(items, menuTitle);

        int selected = inputHandler.readIntInRange(0, items.size());

        if (selected == 0) {
            return Optional.empty();
        }
        return Optional.of(items.get(selected - 1));
    }

    private void printDisplayableMenu(List<? extends Displayable> items, String menuTitle) {
        System.out.println(menuTitle + "(or 0 to skip):");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getDisplayName());
        }
    }

}