package pl.matros.restaurant.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matros.restaurant.model.Cuisine;
import pl.matros.restaurant.model.MenuItem;
import pl.matros.restaurant.model.MenuItemType;
import pl.matros.restaurant.model.OrderItem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InteractiveWaiterTest {
    @Mock
    private OrderService orderService;

    @Mock
    private InputHandler inputHandler;

    @Mock
    private MenuSelector menuSelector;

    @InjectMocks
    private InteractiveWaiter waiter;

    @Test
    void takeOrder_WhenUserWantsNeitherLunchNorDrink_ReturnsEmptyList() {
        // Given
        when(inputHandler.getYesNoAnswer("Would you like to order lunch?")).thenReturn(false);
        when(inputHandler.getYesNoAnswer("Would you like to order a drink?")).thenReturn(false);

        // When
        List<OrderItem> result = waiter.takeOrder();

        // Then
        assertTrue(result.isEmpty());
        verify(inputHandler, times(2)).getYesNoAnswer(any());
        verifyNoMoreInteractions(menuSelector);
    }

    @Test
    void takeOrder_WhenUserWantsOnlyDrink_ReturnsDrinkOrder() {
        // Given
        MenuItem drink = new MenuItem();
        drink.setName("Cola");
        drink.setPrice(200);

        when(inputHandler.getYesNoAnswer("Would you like to order lunch?")).thenReturn(false);
        when(inputHandler.getYesNoAnswer("Would you like to order a drink?")).thenReturn(true);
        when(inputHandler.getYesNoAnswer("Do you wish to add something to your drink?")).thenReturn(false);
        when(orderService.getMenuItemsByType(MenuItemType.DRINK)).thenReturn(List.of(drink));
        when(menuSelector.selectMenuItem(any(), eq("Select drink"))).thenReturn(drink);

        // When
        List<OrderItem> result = waiter.takeOrder();

        // Then
        assertEquals(1, result.size());
        assertEquals(drink, result.get(0).getMenuItem());
        assertTrue(result.get(0).getAdditions().isEmpty());
    }

    @Test
    void takeOrder_WhenUserWantsFullLunch_ReturnsCompleteOrder() {
        // Given
        MenuItem mainCourse = new MenuItem();
        mainCourse.setName("Pasta");
        MenuItem dessert = new MenuItem();
        dessert.setName("Tiramisu");

        when(inputHandler.getYesNoAnswer("Would you like to order lunch?")).thenReturn(true);
        when(inputHandler.getYesNoAnswer("Would you like to order a drink?")).thenReturn(false);

        when(menuSelector.selectMenuItem(any(), eq("cuisine"))).thenReturn(null);

        when(orderService.getMenuItemsByType(MenuItemType.MAIN_COURSE)).thenReturn(List.of(mainCourse));
        when(orderService.getMenuItemsByType(MenuItemType.DESSERT)).thenReturn(List.of(dessert));

        when(menuSelector.selectMenuItem(any(), eq("Select meal"))).thenReturn(mainCourse);
        when(menuSelector.selectMenuItem(any(), eq("Select dessert"))).thenReturn(dessert);

        // When
        List<OrderItem> result = waiter.takeOrder();

        // Then
        assertEquals(2, result.size());
        assertEquals(mainCourse, result.get(0).getMenuItem());
        assertEquals(dessert, result.get(1).getMenuItem());
    }

    @Test
    void takeDrinkOrder_WithAdditions_ReturnsCompleteOrder() {
        // Given
        MenuItem drink = new MenuItem();
        drink.setName("Cola");
        MenuItem ice = new MenuItem();
        ice.setName("Ice");
        MenuItem lemon = new MenuItem();
        lemon.setName("Lemon");

        when(orderService.getMenuItemsByType(MenuItemType.DRINK)).thenReturn(List.of(drink));
        when(orderService.getMenuItemsByType(MenuItemType.DRINK_ADDITION)).thenReturn(List.of(ice, lemon));
        when(menuSelector.selectMenuItem(any(), eq("Select drink"))).thenReturn(drink);
        when(inputHandler.getYesNoAnswer("Do you wish to add something to your drink?")).thenReturn(true);
        when(menuSelector.selectMenuItem(any(), contains("What would you like to add"))).thenReturn(ice, lemon, null);

        // When
        OrderItem result = waiter.takeDrinkOrder();

        // Then
        assertNotNull(result);
        assertEquals(drink, result.getMenuItem());
        assertEquals(2, result.getAdditions().size());
        assertTrue(result.getAdditions().contains(ice));
        assertTrue(result.getAdditions().contains(lemon));
    }

    @Test
    void takeMainCourseOrder_WithSpecificCuisine_ReturnsCorrectOrder() {
        // Given
        Cuisine italianCuisine = new Cuisine();
        italianCuisine.setName("Italian");
        MenuItem pasta = new MenuItem();
        pasta.setName("Pasta");

        when(menuSelector.selectMenuItem(any(), eq("cuisine"))).thenReturn(italianCuisine);
        when(orderService.getMenuItemsByCuisineAndType(italianCuisine, MenuItemType.MAIN_COURSE))
                .thenReturn(List.of(pasta));
        when(menuSelector.selectMenuItem(any(), contains("Select meal from Italian cuisine")))
                .thenReturn(pasta);

        // When
        OrderItem result = waiter.takeMainCourseOrder();

        // Then
        assertNotNull(result);
        assertEquals(pasta, result.getMenuItem());
        assertTrue(result.getAdditions().isEmpty());
    }

    @Test
    void takeDessertOrder_WhenDessertSelected_ReturnsCorrectOrder() {
        // Given
        MenuItem dessert = new MenuItem();
        dessert.setName("Tiramisu");

        when(orderService.getMenuItemsByType(MenuItemType.DESSERT)).thenReturn(List.of(dessert));
        when(menuSelector.selectMenuItem(any(), eq("Select dessert"))).thenReturn(dessert);

        // When
        OrderItem result = waiter.takeDessertOrder();

        // Then
        assertNotNull(result);
        assertEquals(dessert, result.getMenuItem());
        assertTrue(result.getAdditions().isEmpty());
    }
}