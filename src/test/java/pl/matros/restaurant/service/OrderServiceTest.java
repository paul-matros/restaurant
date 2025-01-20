package pl.matros.restaurant.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matros.restaurant.model.MenuItem;
import pl.matros.restaurant.model.MenuItemType;
import pl.matros.restaurant.model.OrderItem;
import pl.matros.restaurant.repository.CuisineRepository;
import pl.matros.restaurant.repository.MenuItemRepository;
import pl.matros.restaurant.repository.PlacedOrderRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private CuisineRepository cuisineRepository;
    @Mock
    private MenuItemRepository menuItemRepository;
    @Mock
    private PlacedOrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void calculateTotalOrderPrice_WithNoAdditions() {
        // Given
        MenuItem mainCourseA = new MenuItem();
        int priceA = 1000;
        mainCourseA.setPrice(priceA);
        OrderItem orderItemA = new OrderItem(mainCourseA);

        MenuItem mainCourseB = new MenuItem();
        int priceB = 1000;
        mainCourseB.setPrice(priceB);
        OrderItem orderItemB = new OrderItem(mainCourseB);

        // When
        int total = orderService.calculateTotalOrderPrice(List.of(orderItemA, orderItemB));

        // Then
        assertEquals(priceA + priceB, total);
    }

    @Test
    void calculateTotalOrderPrice_WithAdditions() {
        // Given
        MenuItem drink = new MenuItem();
        drink.setPrice(500);

        MenuItem ice = new MenuItem();
        ice.setPrice(100);

        MenuItem lemon = new MenuItem();
        lemon.setPrice(50);

        OrderItem orderItem = new OrderItem(drink, List.of(ice, lemon));

        // When
        int total = orderService.calculateTotalOrderPrice(List.of(orderItem));

        // Then
        assertEquals(650, total);
    }

    @Test
    void getMenuItemsByType_ShouldReturnCorrectItems() {
        // Given
        MenuItemType type = MenuItemType.MAIN_COURSE;
        List<MenuItem> expectedItems = List.of(new MenuItem());
        when(menuItemRepository.findByType(type)).thenReturn(expectedItems);

        // When
        List<MenuItem> result = orderService.getMenuItemsByType(type);

        // Then
        assertEquals(expectedItems, result);
        verify(menuItemRepository).findByType(type);
    }
}